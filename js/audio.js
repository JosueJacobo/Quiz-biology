// Audio Synthesizer using Web Audio API
class SoundManager {
  constructor() {
    this.ctx = null;
    this.enabled = true;
    this.initContext();
  }

  initContext() {
    if (!this.ctx && (window.AudioContext || window.webkitAudioContext)) {
      const AudioCtx = window.AudioContext || window.webkitAudioContext;
      this.ctx = new AudioCtx();
    }
  }

  ensureUnlocked() {
    if (this.ctx && this.ctx.state === 'suspended') {
      this.ctx.resume();
    }
  }

  setEnabled(state) {
    this.enabled = state;
  }

  playTone(freq, duration = 0.15, type = 'sine', gainVal = 0.15, delay = 0) {
    if (!this.enabled) return;
    this.initContext();
    if (!this.ctx) return;
    this.ensureUnlocked();

    setTimeout(() => {
      try {
        const osc = this.ctx.createOscillator();
        const gain = this.ctx.createGain();
        osc.type = type;
        osc.frequency.setValueAtTime(freq, this.ctx.currentTime);

        gain.gain.setValueAtTime(gainVal, this.ctx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.0001, this.ctx.currentTime + duration);

        osc.connect(gain);
        gain.connect(this.ctx.destination);

        osc.start();
        osc.stop(this.ctx.currentTime + duration);
      } catch (e) {
        console.warn('Audio playback error', e);
      }
    }, delay * 1000);
  }

  playCorrect() {
    if (!this.enabled) return;
    // Pleasant ascending chord
    this.playTone(523.25, 0.12, 'sine', 0.15, 0);      // C5
    this.playTone(659.25, 0.15, 'sine', 0.18, 0.08);   // E5
    this.playTone(783.99, 0.25, 'triangle', 0.2, 0.16); // G5
    this.vibrate([40, 30, 40]);
  }

  playIncorrect() {
    if (!this.enabled) return;
    this.playTone(220.00, 0.18, 'sawtooth', 0.12, 0);     // A3
    this.playTone(185.00, 0.28, 'sawtooth', 0.15, 0.12);  // F#3
    this.vibrate([80]);
  }

  playClick() {
    if (!this.enabled) return;
    this.playTone(880, 0.04, 'sine', 0.08, 0);
    this.vibrate([15]);
  }

  playTick() {
    if (!this.enabled) return;
    this.playTone(440, 0.03, 'triangle', 0.05, 0);
  }

  playVictory() {
    if (!this.enabled) return;
    // Major fanfare
    const notes = [523.25, 659.25, 783.99, 1046.50]; // C5, E5, G5, C6
    notes.forEach((freq, idx) => {
      this.playTone(freq, 0.25, 'triangle', 0.2, idx * 0.1);
    });
    this.vibrate([60, 50, 60, 50, 120]);
  }

  vibrate(pattern) {
    if (typeof navigator !== 'undefined' && 'vibrate' in navigator) {
      try {
        navigator.vibrate(pattern);
      } catch (e) {
        // ignore on unsupported devices
      }
    }
  }
}

window.sound = new SoundManager();
