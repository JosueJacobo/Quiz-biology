// BioQuiz Main Web Application Engine

class BioQuizApp {
  constructor() {
    this.currentScreen = 'levels'; // 'levels', 'quiz', 'leaderboard', 'profile', 'add_question'
    this.selectedTierFilter = 'all';
    this.searchQuery = '';
    
    // Active Quiz State
    this.activeLevel = null;
    this.activeTier = null;
    this.quizQuestions = [];
    this.currentQIndex = 0;
    this.currentScore = 0;
    this.currentStreak = 0;
    this.highestStreakThisSession = 0;
    this.correctAnswersCount = 0;
    this.timerInterval = null;
    this.timeLeft = 0;
    this.hasAnsweredCurrent = false;

    this.init();
  }

  init() {
    this.bindEvents();
    this.renderHeaderStats();
    this.renderLevelsGrid();
    this.renderLeaderboard();
    this.renderProfile();
    this.setupPWA();
  }

  bindEvents() {
    // Bottom Navigation
    document.querySelectorAll('.nav-item').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const screen = btn.getAttribute('data-screen');
        if (screen) {
          window.sound.playClick();
          this.switchScreen(screen);
        }
      });
    });

    // Sound toggle
    const soundToggle = document.getElementById('sound-toggle-btn');
    if (soundToggle) {
      soundToggle.addEventListener('click', () => {
        const user = window.storage.getUser();
        const newState = !user.soundEnabled;
        window.storage.setSoundEnabled(newState);
        window.sound.setEnabled(newState);
        soundToggle.textContent = newState ? '🔊' : '🔇';
        if (newState) window.sound.playClick();
      });
      // Initial sound state
      const user = window.storage.getUser();
      window.sound.setEnabled(user.soundEnabled);
      soundToggle.textContent = user.soundEnabled ? '🔊' : '🔇';
    }

    // Difficulty filter chips
    document.querySelectorAll('.filter-chip').forEach(chip => {
      chip.addEventListener('click', () => {
        window.sound.playClick();
        document.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
        chip.classList.add('active');
        this.selectedTierFilter = chip.getAttribute('data-tier') || 'all';
        this.renderLevelsGrid();
      });
    });

    // Search levels
    const searchInput = document.getElementById('levels-search-input');
    if (searchInput) {
      searchInput.addEventListener('input', (e) => {
        this.searchQuery = e.target.value.toLowerCase().trim();
        this.renderLevelsGrid();
      });
    }

    // Quiz Navigation
    document.getElementById('quiz-back-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      this.exitQuiz();
    });

    document.getElementById('next-question-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      this.advanceQuestion();
    });

    // Modal Actions
    document.getElementById('modal-retry-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      this.closeModal();
      if (this.activeLevel) this.startQuiz(this.activeLevel.id);
    });

    document.getElementById('modal-next-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      this.closeModal();
      if (this.activeLevel && this.activeLevel.id < 100) {
        this.startQuiz(this.activeLevel.id + 1);
      } else {
        this.switchScreen('levels');
      }
    });

    document.getElementById('modal-close-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      this.closeModal();
      this.switchScreen('levels');
    });

    // Profile Screen Events
    document.querySelectorAll('.avatar-opt').forEach(opt => {
      opt.addEventListener('click', () => {
        window.sound.playClick();
        document.querySelectorAll('.avatar-opt').forEach(o => o.classList.remove('selected'));
        opt.classList.add('selected');
        const avatar = opt.getAttribute('data-avatar');
        window.storage.updateProfile(null, avatar);
        this.renderHeaderStats();
      });
    });

    document.getElementById('save-name-btn')?.addEventListener('click', () => {
      window.sound.playClick();
      const nameInput = document.getElementById('profile-name-input');
      if (nameInput && nameInput.value.trim()) {
        window.storage.updateProfile(nameInput.value.trim(), null);
        this.renderHeaderStats();
        alert('¡Nombre actualizado correctamente!');
      }
    });

    document.getElementById('reset-progress-btn')?.addEventListener('click', () => {
      if (confirm('¿Estás seguro de reiniciar todo el progreso de los 100 niveles?')) {
        window.storage.resetAllProgress();
        this.renderHeaderStats();
        this.renderLevelsGrid();
        this.renderProfile();
        this.renderLeaderboard();
        alert('Progreso reiniciado a Nivel 1.');
      }
    });

    // Add Custom Question Form
    const customForm = document.getElementById('add-question-form');
    if (customForm) {
      customForm.addEventListener('submit', (e) => {
        e.preventDefault();
        window.sound.playClick();
        this.handleCustomQuestionSubmit();
      });
    }
  }

  setupPWA() {
    if ('serviceWorker' in navigator) {
      window.addEventListener('load', () => {
        navigator.serviceWorker.register('./sw.js').catch(err => {
          console.warn('ServiceWorker registration error', err);
        });
      });
    }
  }

  switchScreen(screenName) {
    if (this.timerInterval && screenName !== 'quiz') {
      clearInterval(this.timerInterval);
    }

    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

    const targetScreen = document.getElementById(`screen-${screenName}`);
    if (targetScreen) targetScreen.classList.add('active');

    const targetNav = document.querySelector(`.nav-item[data-screen="${screenName}"]`);
    if (targetNav) targetNav.classList.add('active');

    this.currentScreen = screenName;

    if (screenName === 'levels') {
      this.renderLevelsGrid();
      this.renderHeaderStats();
    } else if (screenName === 'leaderboard') {
      this.renderLeaderboard();
    } else if (screenName === 'profile') {
      this.renderProfile();
    }
  }

  renderHeaderStats() {
    const user = window.storage.getUser();
    const totalStars = window.storage.getTotalStars();
    
    document.getElementById('header-score').textContent = user.totalScore.toLocaleString();
    document.getElementById('header-stars').textContent = totalStars;
    document.getElementById('header-level-badge').textContent = `Niv. ${user.currentLevel}`;
  }

  // =========================================================================
  // LEVELS GRID SCREEN
  // =========================================================================
  renderLevelsGrid() {
    const user = window.storage.getUser();
    const grid = document.getElementById('levels-grid-container');
    if (!grid) return;

    grid.innerHTML = '';

    const completedCount = window.storage.getCompletedLevelsCount();
    const totalStars = window.storage.getTotalStars();
    const progressPercent = Math.min(100, Math.round((completedCount / 100) * 100));

    document.getElementById('levels-progress-bar').style.width = `${Math.max(2, progressPercent)}%`;
    document.getElementById('levels-progress-text').textContent = `${completedCount} / 100 Completados (${progressPercent}%)`;
    document.getElementById('levels-stars-text').textContent = `${totalStars} / 300 ★`;

    // Filter by tier and search query
    const filteredLevels = window.BioData.LEVELS_METADATA.filter(lvl => {
      const tier = window.BioData.getTierForLevel(lvl.id);
      const matchesTier = (this.selectedTierFilter === 'all') || (tier.id === this.selectedTierFilter);
      const matchesSearch = !this.searchQuery || 
        lvl.title.toLowerCase().includes(this.searchQuery) ||
        lvl.desc.toLowerCase().includes(this.searchQuery) ||
        lvl.id.toString() === this.searchQuery;
      return matchesTier && matchesSearch;
    });

    if (filteredLevels.length === 0) {
      grid.innerHTML = `
        <div style="grid-column: 1/-1; text-align: center; padding: 40px 20px; color: var(--text-dim);">
          <div style="font-size: 2rem; margin-bottom: 8px;">🔍</div>
          <p>No se encontraron niveles con ese criterio.</p>
        </div>
      `;
      return;
    }

    filteredLevels.forEach(lvl => {
      const tier = window.BioData.getTierForLevel(lvl.id);
      const isUnlocked = lvl.id <= user.currentLevel;
      const progress = user.levelsProgress[lvl.id] || { completed: false, stars: 0, highScore: 0 };
      const isCurrent = lvl.id === user.currentLevel;

      const card = document.createElement('div');
      card.className = `level-card ${isUnlocked ? '' : 'locked'} ${isCurrent ? 'current' : ''}`;
      
      let starsHTML = '';
      if (progress.completed && progress.stars > 0) {
        starsHTML = '★'.repeat(progress.stars) + '☆'.repeat(3 - progress.stars);
      } else if (isUnlocked) {
        starsHTML = '☆☆☆';
      } else {
        starsHTML = '🔒';
      }

      card.innerHTML = `
        <div class="level-card-top">
          <span class="level-number-badge" style="border-left: 3px solid ${tier.color}">Nivel ${lvl.id}</span>
          <span class="level-icon-badge">${isUnlocked ? lvl.icon : '🔒'}</span>
        </div>
        <div class="level-card-title">${lvl.title}</div>
        <div class="level-card-tier" style="color: ${tier.color}">${tier.icon} ${tier.name}</div>
        <div class="level-card-footer">
          <div class="stars-row ${progress.stars === 0 ? 'empty' : ''}">${starsHTML}</div>
          <div class="questions-count-badge">50+ preg.</div>
        </div>
      `;

      if (isUnlocked) {
        card.addEventListener('click', () => {
          window.sound.playClick();
          this.startQuiz(lvl.id);
        });
      }

      grid.appendChild(card);
    });
  }

  // =========================================================================
  // QUIZ GAME ENGINE
  // =========================================================================
  startQuiz(levelId) {
    this.activeLevel = window.BioData.LEVELS_METADATA.find(l => l.id === levelId) || window.BioData.LEVELS_METADATA[0];
    this.activeTier = window.BioData.getTierForLevel(levelId);
    this.quizQuestions = window.BioData.getQuizSessionQuestions(levelId);
    this.currentQIndex = 0;
    this.currentScore = 0;
    this.currentStreak = 0;
    this.highestStreakThisSession = 0;
    this.correctAnswersCount = 0;

    // Header info
    document.getElementById('quiz-level-indicator-text').textContent = `Nivel ${this.activeLevel.id} • ${this.activeTier.name}`;
    document.getElementById('quiz-streak-counter').textContent = '🔥 0';

    this.switchScreen('quiz');
    this.renderCurrentQuestion();
  }

  renderCurrentQuestion() {
    if (this.timerInterval) clearInterval(this.timerInterval);
    this.hasAnsweredCurrent = false;

    const q = this.quizQuestions[this.currentQIndex];
    if (!q) return;

    // Counter & meta
    document.getElementById('quiz-question-counter').textContent = `Pregunta ${this.currentQIndex + 1} de ${this.quizQuestions.length}`;
    document.getElementById('quiz-points-val').textContent = `+${this.activeTier.pointsPerQuestion + (this.currentStreak * 25)} pts`;
    document.getElementById('quiz-question-text').textContent = q.question;

    // Options
    const optionsContainer = document.getElementById('quiz-options-container');
    optionsContainer.innerHTML = '';

    const letters = ['A', 'B', 'C', 'D'];
    q.options.forEach((optText, idx) => {
      const btn = document.createElement('button');
      btn.className = 'option-btn';
      btn.innerHTML = `
        <span class="option-letter">${letters[idx]}</span>
        <span class="option-text">${optText}</span>
      `;
      btn.addEventListener('click', () => this.handleOptionSelection(idx));
      optionsContainer.appendChild(btn);
    });

    // Hide explanation and next button initially
    const explanationEl = document.getElementById('quiz-explanation-box');
    explanationEl.style.display = 'none';
    explanationEl.textContent = '';

    const nextBtn = document.getElementById('next-question-btn');
    nextBtn.style.display = 'none';

    // Start Timer
    this.startTimer();
  }

  startTimer() {
    const totalTime = this.activeTier.timeLimit || 15;
    this.timeLeft = totalTime;
    const timerFill = document.getElementById('quiz-timer-bar');
    timerFill.style.width = '100%';
    timerFill.className = 'quiz-timer-bar-fill';

    const intervalStepMs = 100;
    const decrement = intervalStepMs / (totalTime * 1000);
    let currentPct = 1.0;

    this.timerInterval = setInterval(() => {
      currentPct -= decrement;
      if (currentPct <= 0) {
        clearInterval(this.timerInterval);
        timerFill.style.width = '0%';
        this.handleTimeExpired();
      } else {
        const pctWidth = (currentPct * 100).toFixed(1);
        timerFill.style.width = `${pctWidth}%`;
        if (currentPct < 0.25) {
          timerFill.className = 'quiz-timer-bar-fill danger';
        } else if (currentPct < 0.5) {
          timerFill.className = 'quiz-timer-bar-fill warning';
        }
      }
    }, intervalStepMs);
  }

  handleOptionSelection(selectedIndex) {
    if (this.hasAnsweredCurrent) return;
    this.hasAnsweredCurrent = true;
    if (this.timerInterval) clearInterval(this.timerInterval);

    const q = this.quizQuestions[this.currentQIndex];
    const isCorrect = selectedIndex === q.correctIndex;
    const optionButtons = document.querySelectorAll('.option-btn');

    // Disable all options
    optionButtons.forEach(btn => btn.disabled = true);

    if (isCorrect) {
      window.sound.playCorrect();
      this.currentStreak++;
      this.highestStreakThisSession = Math.max(this.highestStreakThisSession, this.currentStreak);
      this.correctAnswersCount++;

      const earnedPoints = this.activeTier.pointsPerQuestion + (this.currentStreak * 25);
      this.currentScore += earnedPoints;

      optionButtons[selectedIndex].classList.add('correct');
    } else {
      window.sound.playIncorrect();
      this.currentStreak = 0;
      if (selectedIndex !== null && optionButtons[selectedIndex]) {
        optionButtons[selectedIndex].classList.add('incorrect');
      }
      // Reveal correct option
      if (optionButtons[q.correctIndex]) {
        optionButtons[q.correctIndex].classList.add('correct');
      }
    }

    // Update streak badge in UI
    document.getElementById('quiz-streak-counter').textContent = `🔥 ${this.currentStreak}`;

    // Reveal explanation
    const explanationEl = document.getElementById('quiz-explanation-box');
    explanationEl.innerHTML = `<strong>💡 Explicación Científica:</strong> ${q.explanation}`;
    explanationEl.style.display = 'block';

    // Show Next Button
    const nextBtn = document.getElementById('next-question-btn');
    nextBtn.textContent = (this.currentQIndex + 1 < this.quizQuestions.length) ? 'Siguiente Pregunta ➔' : 'Ver Resultados ★';
    nextBtn.style.display = 'block';
  }

  handleTimeExpired() {
    if (this.hasAnsweredCurrent) return;
    this.handleOptionSelection(null);
  }

  advanceQuestion() {
    this.currentQIndex++;
    if (this.currentQIndex < this.quizQuestions.length) {
      this.renderCurrentQuestion();
    } else {
      this.finishQuizSession();
    }
  }

  finishQuizSession() {
    const totalQ = this.quizQuestions.length;
    const accuracy = Math.round((this.correctAnswersCount / totalQ) * 100);

    let stars = 0;
    if (accuracy >= 80) stars = 3;
    else if (accuracy >= 50) stars = 2;
    else if (accuracy >= 30) stars = 1;

    // Save results
    window.storage.recordLevelResult(
      this.activeLevel.id,
      this.currentScore,
      stars,
      this.correctAnswersCount,
      totalQ,
      this.highestStreakThisSession
    );

    this.renderHeaderStats();

    if (stars > 0) {
      window.sound.playVictory();
    } else {
      window.sound.playIncorrect();
    }

    // Populate Modal
    const modalStars = document.getElementById('modal-stars-row');
    modalStars.textContent = stars > 0 ? '★'.repeat(stars) + '☆'.repeat(3 - stars) : '☆☆☆';

    document.getElementById('modal-title').textContent = stars > 0 ? '¡Nivel Superado!' : '¡Sigue Intentando!';
    document.getElementById('modal-subtitle').textContent = stars > 0 
      ? `Has demostrado un gran dominio en ${this.activeLevel.title}.`
      : 'Necesitas al menos 1 estrella (30% de aciertos) para avanzar.';

    document.getElementById('modal-score-val').textContent = this.currentScore.toLocaleString();
    document.getElementById('modal-accuracy-val').textContent = `${accuracy}%`;
    document.getElementById('modal-streak-val').textContent = `🔥 ${this.highestStreakThisSession}`;

    const nextBtn = document.getElementById('modal-next-btn');
    if (stars > 0 && this.activeLevel.id < 100) {
      nextBtn.style.display = 'block';
    } else {
      nextBtn.style.display = 'none';
    }

    document.getElementById('quiz-result-modal').classList.add('active');
  }

  closeModal() {
    document.getElementById('quiz-result-modal').classList.remove('active');
  }

  exitQuiz() {
    if (confirm('¿Deseas salir de la partida actual? El progreso de este intento se perderá.')) {
      if (this.timerInterval) clearInterval(this.timerInterval);
      this.switchScreen('levels');
    }
  }

  // =========================================================================
  // LEADERBOARD SCREEN
  // =========================================================================
  renderLeaderboard() {
    const user = window.storage.getUser();

    const mockLegends = [
      { name: "Lynn Margulis", avatar: "🔬", score: 28400, desc: "Teoría Endosimbiótica" },
      { name: "Charles Darwin", avatar: "⛵", score: 26150, desc: "El Origen de las Especies" },
      { name: "Gregor Mendel", avatar: "🌱", score: 23900, desc: "Padre de la Genética" },
      { name: "Jane Goodall", avatar: "🐒", score: 21300, desc: "Primatología de Campo" },
      { name: "A. von Humboldt", avatar: "🧭", score: 18700, desc: "Geografía de las Plantas" },
      { name: "Rachel Carson", avatar: "🌊", score: 15400, desc: "Primavera Silenciosa" },
      { name: "Carl Linneo", avatar: "📖", score: 12800, desc: "Nomenclatura Binomial" }
    ];

    // Insert current user in ranking
    const allRanks = [...mockLegends, {
      name: `${user.playerName} (Tú)`,
      avatar: user.avatar,
      score: user.totalScore,
      desc: `Nivel ${user.currentLevel} • ${window.storage.getCompletedLevelsCount()} completados`,
      isUser: true
    }].sort((a, b) => b.score - a.score);

    // Podium (Top 3)
    const first = allRanks[0] || mockLegends[0];
    const second = allRanks[1] || mockLegends[1];
    const third = allRanks[2] || mockLegends[2];

    const podium = document.getElementById('leaderboard-podium');
    if (podium) {
      podium.innerHTML = `
        <div class="podium-step second">
          <div class="podium-avatar">${second.avatar}</div>
          <div class="podium-name">${second.name}</div>
          <div class="podium-score">${second.score.toLocaleString()} pts</div>
          <div class="podium-block">2</div>
        </div>
        <div class="podium-step first">
          <div class="podium-avatar">${first.avatar}</div>
          <div class="podium-name">${first.name}</div>
          <div class="podium-score">${first.score.toLocaleString()} pts</div>
          <div class="podium-block">1 👑</div>
        </div>
        <div class="podium-step third">
          <div class="podium-avatar">${third.avatar}</div>
          <div class="podium-name">${third.name}</div>
          <div class="podium-score">${third.score.toLocaleString()} pts</div>
          <div class="podium-block">3</div>
        </div>
      `;
    }

    // List of Ranks
    const listContainer = document.getElementById('leaderboard-list');
    if (listContainer) {
      listContainer.innerHTML = '';
      allRanks.forEach((item, index) => {
        const row = document.createElement('div');
        row.className = `leaderboard-row ${item.isUser ? 'current-user' : ''}`;
        row.innerHTML = `
          <div class="row-rank">#${index + 1}</div>
          <div class="row-avatar">${item.avatar}</div>
          <div class="row-info">
            <div class="row-name">${item.name}</div>
            <div class="row-sub">${item.desc}</div>
          </div>
          <div class="row-score">${item.score.toLocaleString()} pts</div>
        `;
        listContainer.appendChild(row);
      });
    }
  }

  // =========================================================================
  // PROFILE SCREEN
  // =========================================================================
  renderProfile() {
    const user = window.storage.getUser();
    const completed = window.storage.getCompletedLevelsCount();
    const totalStars = window.storage.getTotalStars();
    const accuracy = user.totalQuestionsAnswered > 0
      ? Math.round((user.totalCorrectAnswers / user.totalQuestionsAnswered) * 100)
      : 0;

    // Profile fields
    const nameInput = document.getElementById('profile-name-input');
    if (nameInput) nameInput.value = user.playerName;

    document.querySelectorAll('.avatar-opt').forEach(opt => {
      if (opt.getAttribute('data-avatar') === user.avatar) {
        opt.classList.add('selected');
      } else {
        opt.classList.remove('selected');
      }
    });

    // Rank title
    let rankTitle = 'Biólogo Principiante';
    if (user.totalScore > 20000) rankTitle = 'Biólogo Emérito Legendario 👑';
    else if (user.totalScore > 10000) rankTitle = 'Científico Senior de la Biosfera 🔬';
    else if (user.totalScore > 5000) rankTitle = 'Taxónomo e Investigador de Campo 🌲';
    else if (user.totalScore > 1500) rankTitle = 'Explorador Botánico y Zoológico 🌿';

    document.getElementById('profile-rank-title').textContent = rankTitle;
    document.getElementById('profile-total-score').textContent = user.totalScore.toLocaleString();
    document.getElementById('profile-levels-completed').textContent = `${completed} / 100`;
    document.getElementById('profile-stars-total').textContent = `${totalStars} ★`;
    document.getElementById('profile-best-streak').textContent = `🔥 ${user.highestStreak}`;
    document.getElementById('profile-accuracy').textContent = `${accuracy}%`;
  }

  // =========================================================================
  // ADD CUSTOM QUESTION
  // =========================================================================
  handleCustomQuestionSubmit() {
    const qText = document.getElementById('custom-q-text')?.value.trim();
    const cat = document.getElementById('custom-q-category')?.value;
    const opt0 = document.getElementById('custom-q-opt0')?.value.trim();
    const opt1 = document.getElementById('custom-q-opt1')?.value.trim();
    const opt2 = document.getElementById('custom-q-opt2')?.value.trim();
    const opt3 = document.getElementById('custom-q-opt3')?.value.trim();
    const correctIdx = parseInt(document.getElementById('custom-q-correct')?.value || '0', 10);
    const explanation = document.getElementById('custom-q-expl')?.value.trim();

    if (!qText || !opt0 || !opt1 || !opt2 || !opt3 || !explanation) {
      alert('Por favor completa todos los campos del formulario.');
      return;
    }

    const newQuestion = {
      id: `custom_${Date.now()}`,
      category: cat,
      question: qText,
      options: [opt0, opt1, opt2, opt3],
      correctIndex: correctIdx,
      explanation: explanation
    };

    window.storage.saveCustomQuestion(newQuestion);
    alert('¡Pregunta guardada exitosamente! Se integrará a tus partidas.');

    document.getElementById('add-question-form')?.reset();
    this.switchScreen('levels');
  }
}

// Boot application on DOM ready
document.addEventListener('DOMContentLoaded', () => {
  window.app = new BioQuizApp();
});
