const sliders = document.querySelectorAll('.vertical-slider');
console.log(sliders)
const audioElements = [];

sliders.forEach(function (slider) {
    const audio = new Audio(slider.dataset.audioPath);
    audio.play();
    audioElements.push({ slider, audio });

    slider.addEventListener('input', function (event) {
        audio.volume = event.target.value / 100;
    });
});
let isAudioPlaying = true;
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

document.getElementById('preset_button').addEventListener('click', function () {
    var menuList = document.getElementById('menu-list');
    menuList.style.display = (menuList.style.display === 'none' || menuList.style.display === '') ? 'block' : 'none';
});