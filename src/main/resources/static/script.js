const audio1 = new Audio('fire.mp3');
const audio2 = new Audio('wind.mp3');

document.getElementById('audio1').addEventListener('input', function (event) {
	audio1.volume = event.target.value / 100;
});

document.getElementById('audio2').addEventListener('input', function (event) {
	audio2.volume = event.target.value / 100;
});

document.addEventListener('keydown', function (event) {
        // Check if the pressed key is the spacebar (key code 32)
        if (event.code === 'Space') {
            // Play all audio elements
            audio1.play();
            audio2.play();
            // Add more audio elements if needed
        }
    });