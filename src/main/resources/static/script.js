 const sliders = document.querySelectorAll('.vertical-slider');
const audioElements = [];

sliders.forEach(function (slider) {
    const audio = new Audio(slider.dataset.audioPath);
    audioElements.push({ slider, audio });

    slider.addEventListener('input', function (event) {
        audio.volume = event.target.value / 100;
    });
});
let isAudioPlaying = false;
document.addEventListener('keydown', function (event) {
    if (event.code === 'Space') {
        audioElements.forEach(function (item) {
            const { slider, audio } = item;
            if (!isAudioPlaying) {
                audio.play();
            }
            else{
                audio.pause();
            }

        });
    }
    isAudioPlaying = !isAudioPlaying;
});