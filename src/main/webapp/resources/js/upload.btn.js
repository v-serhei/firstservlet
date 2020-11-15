let inputs = document.querySelectorAll('.input__file');
Array.prototype.forEach.call(inputs, function (input) {
    let label = input.nextElementSibling,
        labelVal = label.querySelector('.input__file-button-text').innerText;

    input.addEventListener('change', function (e) {
        let countFiles = '';
        if (this.files && this.files.length >= 1)
            countFiles = this.files.length;
        let x = document.getElementById("input__file");
        let fileSize =  Math.floor(x.files[0].size/1024/1024 * 100) / 100

        if (countFiles)
            label.querySelector('.input__file-button-text').innerText = x.files[0].name + " " + fileSize +"MB";
        else
            label.querySelector('.input__file-button-text').innerText = labelVal;
    });
});