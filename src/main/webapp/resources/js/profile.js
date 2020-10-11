function showSettingsContent() {
    let showBlockId = 'profile-account-settings';
    let hideBlockIdFirst = 'profile-review-block';
    let hideBlockIdSecond = 'profile-order-block';
    document.getElementById(showBlockId).style.display = "flex";
    document.getElementById(hideBlockIdFirst).style.display = "none";
    document.getElementById(hideBlockIdSecond).style.display = "none";

}

function showOrderContent() {
    let showBlockId = 'profile-order-block';
    let hideBlockIdFirst = 'profile-review-block';
    let hideBlockIdSecond = 'profile-account-settings';
    document.getElementById(showBlockId).style.display = "flex";
    document.getElementById(hideBlockIdFirst).style.display = "none";
    document.getElementById(hideBlockIdSecond).style.display = "none";

}

function showReviewContent() {
    let showBlockId = 'profile-review-block';
    let hideBlockIdFirst = 'profile-account-settings';
    let hideBlockIdSecond = 'profile-order-block';
    document.getElementById(showBlockId).style.display = "flex";
    document.getElementById(hideBlockIdFirst).style.display = "none";
    document.getElementById(hideBlockIdSecond).style.display = "none";

}