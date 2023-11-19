function allowDropFlower(ev) {
    ev.preventDefault();
}

function dragFlower(ev) {
    var dragData = {
        flowerNm: ev.target.innerText,
        englishNm: ev.target.getAttribute('data-englishnm')
    };
    ev.dataTransfer.setData("text", JSON.stringify(dragData));
}

function dropFlower(ev) {
    ev.preventDefault();
    var dragData = JSON.parse(ev.dataTransfer.getData("text"));
    var droppedFlowersTable = document.getElementById("droppedFlowersBody");

    // 기존에 드롭된 꽃이 있으면 모두 제거
    while (droppedFlowersTable.firstChild) {
        droppedFlowersTable.removeChild(droppedFlowersTable.firstChild);
    }

    // 새로 드롭된 꽃 추가
    var newTableRow = droppedFlowersTable.insertRow();
    var newCell = newTableRow.insertCell(0);
    newCell.innerText = dragData.flowerNm;

    var promptInput = document.getElementById("prompt");
    promptInput.value = dragData.englishNm; // 기존에 저장된 꽃의 영문 이름을 새로운 것으로 대체

    var dropZoneImage = document.getElementById("dropZoneImage");
    dropZoneImage.src = "/front/images/openedEnvelope.png";
}

// checkIfTableIsEmptyAndRemoveRow 함수는 더 이상 필요하지 않으므로 제거
