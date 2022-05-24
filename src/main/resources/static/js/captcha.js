$(document).ready(function(){
    onPageReady();
})

function onPageReady(){
    getImageSource();

    var $modal = document.querySelector('.slider-captcha');

    ['.captcha-mask'].forEach(function(selector){
        var $el = $modal.querySelector(selector);
        $el.onmousedown = hideModal;

    });

    var $question = document.querySelector('.questionDiv');
    $question.onclick = reset;

    var $slider = document.querySelector('.slider');


    $slider.addEventListener("mousedown", function(event){
        sliderListener(event);
    })

};
function getImageSource(){
    var $questionImg = document.querySelector('.questionImg');
    var $answerImg = document.querySelector('.answerImg');

    const Http = new XMLHttpRequest();
    const url='/requestImg';
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function (){
        if(Http.readyState === XMLHttpRequest.DONE && Http.status === 200) {
            var result = JSON.parse(Http.response);

            if(result){
                var timeStamp = Date.parse(new Date());
                $questionImg.setAttribute("src", "/questionImg?timestamp="+timeStamp);
                $answerImg.setAttribute("src", "/answerImg?timestamp="+timeStamp);
            }else{
                alert("网络异常，请稍后重试！");
                return;
            }
        }else if(Http.readyState === XMLHttpRequest.DONE && Http.status === 500) {
            alert("服务器异常，请稍后重试！");
            return;
        }
    }
}

function hideModal(){
    window.location.href = location.href.replace(location.pathname, "");
}

function calculateImageMoveX(moveX, imageWidth, maxMove){
    return moveX * imageWidth / maxMove;
}


function sliderListener(event){
    var moveEnable = true;
    var mousedownFlag = false;
    var sliderInitOffset = 0;
    var moveX = 0;

    var $slider = document.querySelector('.slider');
    var $sliderMask = document.querySelector('.sliderMask');
    var $sliderContainer = document.querySelector('.sliderContainer');
    var $answer = document.querySelector('.answerImg');
    var imageWidth = $answer.offsetWidth * 0.9;
    var MIN_MOVE = 0;
    var MAX_MOVE = $sliderContainer.offsetWidth - $slider.offsetWidth;

    $sliderContainer.classList.add("sliderContainer_active");
    sliderInitOffset = event.clientX;
    mousedownFlag = true;
    document.onmousemove=function(event){
        if(mousedownFlag  == false){
            return;
        }
        if(moveEnable == false){
            return
        }

        moveX = event.clientX - sliderInitOffset;

        moveX<MIN_MOVE?moveX=MIN_MOVE:moveX=moveX;
        moveX>MAX_MOVE?moveX=MAX_MOVE:moveX=moveX;


        var imageMoveX = calculateImageMoveX(moveX, imageWidth, MAX_MOVE);

        $slider.setAttribute("style","left:" + moveX + "px");
        $sliderMask.setAttribute("style","width:" + moveX + "px");
        $answer.setAttribute("style","left: -" + imageMoveX + "px");
    }

    document.onmouseup = function(event){
        var imgMoveX = moveX / MAX_MOVE;
        sliderInitOffset = 0;
        mousedownFlag=false;
        checkLocation(imgMoveX);
        document.onmousemove = null;
        document.onmouseup = null;

    }

}

function success(){
    alert("success");
    reset();
}

function resetLocation(){
    var $slider = document.querySelector('.slider');
    var $sliderMask = document.querySelector('.sliderMask');
    var $sliderContainer = document.querySelector('.sliderContainer');
    var $answer = document.querySelector('.answerImg');

    $sliderContainer.classList.remove("sliderContainer_success");
    $sliderContainer.classList.remove("sliderContainer_fail");
    $sliderContainer.classList.remove("sliderContainer_active");
    $slider.setAttribute("style","left:0px");
    $sliderMask.setAttribute("style","width:0px");
    $answer.setAttribute("style","left:0px");
}

function reset(){
    resetLocation();
    getImageSource();
}

function checkLocation(move){
    var $sliderContainer = document.querySelector('.sliderContainer');

    const Http = new XMLHttpRequest();
    const url='/checkLocation';
    Http.open("POST", url);
    Http.send(JSON.stringify({
        moveX: move,
    }));

    Http.onreadystatechange = function (){
        if(Http.readyState === XMLHttpRequest.DONE && Http.status === 200) {
            var checkResult = JSON.parse(Http.response);

            if(checkResult){
                $sliderContainer.classList.add("sliderContainer_success");
                $sliderContainer.removeEventListener("mousedown", function(event){
                    sliderListener(event, selector);
                });

                setTimeout(function (){
                    success();
                }, 500);

            }else{
                $sliderContainer.classList.add("sliderContainer_fail");
                setTimeout(function (){
                    reset();
                }, 500);
            }
        }else if(Http.readyState === XMLHttpRequest.DONE && Http.status === 500) {
            alert("会话已过期，请刷新重试！");
            return;
        }
    }

}