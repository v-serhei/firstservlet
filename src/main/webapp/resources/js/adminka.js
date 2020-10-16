function hideContent (buttonId, showBlockId) {
    let arr = document.getElementsByClassName("admin-page-content");
    let showElement=document.getElementById(showBlockId);
    for( i = 0; i < arr.length; ++i) {
        if (arr[i] === showElement) {
            arr[i].style.display = "flex"
        }else {
            arr[i].style.display = "none"
        }
    }
    let pressedButton = document.getElementById(buttonId);
    let adminButtons = document.getElementsByClassName ("btn-admin-menu");
    for( i = 0; i < adminButtons.length; ++i) {
        if (adminButtons[i] === pressedButton) {
            adminButtons[i] .classList.remove('bg-secondary');
            adminButtons[i].classList.add ('btn-light');

        }else {
            adminButtons[i] .classList.add('bg-secondary');
            adminButtons[i].classList.remove ('btn-light');
        }
    }
}