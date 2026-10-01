// LocalStorage Data Layer for BioQuiz
const STORAGE_KEY = 'bioquiz_save_data_v1';
const CUSTOM_QUESTIONS_KEY = 'bioquiz_custom_questions_v1';

const defaultUserData = {
  playerName: 'Biólogo Naturalista',
  avatar: '🌿',
  totalScore: 0,
  currentLevel: 1, // 1 to 100
  levelsProgress: {
    // levelId: { completed: true, stars: 3, highScore: 1250, attempts: 2 }
  },
  highestStreak: 0,
  totalQuestionsAnswered: 0,
  totalCorrectAnswers: 0,
  soundEnabled: true,
  createdAt: Date.now()
};

class StorageManager {
  constructor() {
    this.data = this.loadData();
    this.customQuestions = this.loadCustomQuestions();
  }

  loadData() {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      if (raw) {
        return { ...defaultUserData, ...JSON.parse(raw) };
      }
    } catch (e) {
      console.warn('Error loading storage', e);
    }
    return { ...defaultUserData };
  }

  saveData() {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(this.data));
    } catch (e) {
      console.error('Error saving storage', e);
    }
  }

  loadCustomQuestions() {
    try {
      const raw = localStorage.getItem(CUSTOM_QUESTIONS_KEY);
      if (raw) {
        return JSON.parse(raw);
      }
    } catch (e) {
      console.warn('Error loading custom questions', e);
    }
    return [];
  }

  saveCustomQuestion(question) {
    this.customQuestions.push(question);
    try {
      localStorage.setItem(CUSTOM_QUESTIONS_KEY, JSON.stringify(this.customQuestions));
    } catch (e) {
      console.error('Error saving custom question', e);
    }
  }

  getUser() {
    return this.data;
  }

  updateProfile(name, avatar) {
    if (name) this.data.playerName = name.trim();
    if (avatar) this.data.avatar = avatar;
    this.saveData();
  }

  setSoundEnabled(enabled) {
    this.data.soundEnabled = enabled;
    this.saveData();
  }

  recordLevelResult(levelId, score, stars, correctCount, totalQuestions, streak) {
    if (!this.data.levelsProgress[levelId]) {
      this.data.levelsProgress[levelId] = {
        completed: false,
        stars: 0,
        highScore: 0,
        attempts: 0
      };
    }

    const current = this.data.levelsProgress[levelId];
    current.attempts += 1;
    current.completed = true;
    current.stars = Math.max(current.stars, stars);
    current.highScore = Math.max(current.highScore, score);

    // Unlock next level if this level is passed with at least 1 star
    if (stars >= 1 && levelId >= this.data.currentLevel && levelId < 100) {
      this.data.currentLevel = levelId + 1;
    }

    this.data.totalScore += score;
    this.data.totalQuestionsAnswered += totalQuestions;
    this.data.totalCorrectAnswers += correctCount;
    this.data.highestStreak = Math.max(this.data.highestStreak, streak);

    this.saveData();
  }

  getCompletedLevelsCount() {
    return Object.values(this.data.levelsProgress).filter(p => p.completed).length;
  }

  getTotalStars() {
    return Object.values(this.data.levelsProgress).reduce((acc, p) => acc + (p.stars || 0), 0);
  }

  resetAllProgress() {
    this.data = {
      ...defaultUserData,
      playerName: this.data.playerName,
      avatar: this.data.avatar,
      createdAt: Date.now()
    };
    this.saveData();
  }
}

window.storage = new StorageManager();
