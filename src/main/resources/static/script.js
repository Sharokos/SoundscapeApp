const sliders = document.querySelectorAll('.vertical-slider');
const freqBtns = document.querySelectorAll('.soundscapeText');
const audioElements = [];

document.addEventListener("DOMContentLoaded", function() {
        console.log("Page loaded")
      setVolume();
      setOpacity();
    });

function setVolume() {
sliders.forEach(function (slider) {
    const audio = new Audio(slider.dataset.audioPath);

    // This will get only the sound name from the id
    btnId = "button_" + slider.id.split("_")[1]

    const freqButton = document.getElementById(btnId);
    var volume = slider.value;

    var volumeLevel = volume / 100;

    audio.volume = volumeLevel;
    if (slider.getAttribute('data-sound-drone') == "true"){
        audio.play();
        audio.loop = true;
        freqButton.disabled = true;

    }

    audioElements.push({ slider, audio, freqButton });
    slider.addEventListener('input', function (event) {
        audio.volume = event.target.value / 100;
    });
});
};

let isAudioPlaying = true;
document.addEventListener('keydown', function (event) {
    var activeElement = document.activeElement;
    var isInputField = activeElement.tagName === 'INPUT' || activeElement.tagName === 'TEXTAREA';

    if (event.code === 'Space') {
        audioElements.forEach(function (item) {
            const { slider, audio, freqButton} = item;
            if (!isAudioPlaying) {
                audio.play();
            }
            else{
                audio.pause();
            }

        });
        event.preventDefault();
    }
    isAudioPlaying = !isAudioPlaying;
});

document.getElementById('preset_button').addEventListener('click', function () {
    var menuList = document.getElementById('menu-list');
    menuList.style.display = (menuList.style.display === 'none' || menuList.style.display === '') ? 'block' : 'none';
});

function runIndefinitely(slider, audio, freqButton) {

  var freq = parseInt(freqButton.getAttribute('data-preset-sound-frequency'));
  var timer = computeTimer(freq);

  setInterval(() => {
    freq = parseInt(freqButton.getAttribute('data-preset-sound-frequency'));
    var randProb = getRandomPercentage();
    var prob = getProbabilityFromFrequency(freq);

    console.log("for the audio: " + slider.id)
    console.log("Probability: " + prob)
    console.log("Random generated: " + randProb)
    console.log("Frequency: " + freq)
    console.log("Will play again in : " + timer)
    // First, check if the sound should be playing at all;
    if (isAudioPlaying) {
      // Determine if the sound will play or not
      if (checkProbability(randProb, prob)) {
        audio.play();
        console.log(slider.id + " is now playing!")
      }
    }
    // Determine the new timer so the audio will not get too predictable
    timer = computeTimer(freq);
  }, timer);
}


audioElements.forEach(function (item) {
            const { slider, audio, freqButton} = item;
            if (slider.getAttribute('data-sound-drone') == "false"){
                runIndefinitely(slider, audio, freqButton);
            }
        });

function computeTimer(freq){
    intervalTimer = 1000;
    secsInMsecs = 1000;
    switch (freq) {
      case 0:
        // Each ~ 30 seconds
        intervalTimer = Math.random() * 10 * secsInMsecs + 25 * secsInMsecs; //anything between 25 and 35 seconds
        break;
      case 1:
        // Each ~ 15 seconds
        intervalTimer = Math.random() * 8 * secsInMsecs + 11 * secsInMsecs; //anything between 11 and 19 seconds
        break;
      case 2:
        // Each ~ 5 seconds
        intervalTimer = Math.random() * 4 * secsInMsecs + 3 * secsInMsecs; //anything between 3 and 7 seconds
        break;
      default:
        console.log("Should not be reachable");
    }
    return intervalTimer;
}

function getRandomPercentage(){
    return Math.random() * 100;
}
function checkProbability(randPerc, probability){
    if (randPerc < probability){
        return true;
    }
    else{
        return false;
    }
}
function getProbabilityFromFrequency(freq){
    prob = 50;
    switch (freq) {
      case 0:
        // 25 - 35 %
        prob = Math.random() * 10 + 25;
        break;
      case 1:
        // 45 - 55 %
        prob = Math.random() * 10 + 45;
        break;
      case 2:
        // 60 - 70 %
        prob = Math.random() * 10 + 60;
        break;
      default:
        console.log("Should not be reachable");
    }
    return prob;
}

function getOpacityFromFrequency(freq){
    op = 1;
    console.log("Freq in function: "+freq)
    switch (freq) {
      case 0:
        // 25 - 35 %
        op = 0.1;
        break;
      case 1:
        // 45 - 55 %
        op = 0.5;
        break;
      case 2:
        // 60 - 70 %
        op = 1;
        break;
      default:
        console.log("Should not be reachable");
    }
    console.log("Opacity in function: " + op)
    return op;

}
function incrementFrequency(id){
    var btnFreq = document.getElementById(id);
    var freq = btnFreq.getAttribute('data-preset-sound-frequency')
    console.log("Frequency read from html: " + freq);


    freq = parseInt(freq);


    freq = freq + 1;
    if (freq>2){
        freq = 0;
    }
    var op = getOpacityFromFrequency(freq);
    btnFreq.style.opacity = op;
    btnFreq.setAttribute('data-preset-sound-frequency',freq)

    btnFreq.setAttribute('value',freq)
}

function setOpacity(){
    console.log("SetOpacity Called")
    freqBtns.forEach(function (frqBtn) {
        var freq = frqBtn.getAttribute('data-preset-sound-frequency')
        audioId = "audio_" + frqBtn.id.split("_")[1]
        const audioSlider = document.getElementById(btnId);
        var isDrone = audioSlider.getAttribute('data-sound-drone')
        if (isDrone == 'false') {
            freq = parseInt(freq);
            var op = getOpacityFromFrequency(freq);
            frqBtn.style.opacity = op;
        }
    });

}

