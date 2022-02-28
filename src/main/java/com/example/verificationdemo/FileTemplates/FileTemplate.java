package com.example.verificationdemo.FileTemplates;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.ClassUtils;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.Charset;

public class FileTemplate {
    public static String htmlFile = "<!DOCUMENT html><html><head><title>captcha</title><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><script src=\"static/js/jquery.js\"></script><link href=\"static/css/font-awesome.min.css\" rel=\"stylesheet\"><link href=\"static/css/captcha.css\" rel=\"stylesheet\"></head><body><button class=\"btn\">安全验证</button><div class=\"slider-captcha\"><div class=\"captcha-mask\"></div><div class=\"captcha-body\"><div class=\"card\"><div class=\"card-header\"><span>请完成安全验证!</span></div><div class=\"card-body\"><div id=\"captcha\"><div class=\"questionDiv\"><img class=\"questionImg\" src=\"data:image/jpg;base64,%s\"/><img class=\"questionImg\" src=\"data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7\"/></div><div class=\"answerOneDiv\"><img class=\"answerImg one\" src=\"data:image/jpg;base64,%s\"/></div><div class=\"answerTwoDiv\"><img class=\"answerImg two\" src=\"data:image/jpg;base64,%s\"/></div><div class=\"sliderContainer one\"><div class=\"sliderBg one\"></div><div class=\"sliderMask one\"><div class=\"slider one\"><i class=\"sliderIcon fa fa-arrow-right\"></i></div></div></div><div class=\"sliderContainer two\"><div class=\"sliderBg two\"></div><div class=\"sliderMask two\"><div class=\"slider two\"><i class=\"sliderIcon fa fa-arrow-right\"></i></div></div></div></div></div></div></div></div><script>%s</script></body></html>";

    public static String jsFile = "onPageReady();function onPageReady(){var $btn=document.querySelector(\".btn\");var $modal=document.querySelector(\".slider-captcha\");$btn.onmousedown=function(){$modal.setAttribute(\"style\",\"display: block\")};[\".captcha-mask\"].forEach(function(selector){var $el=$modal.querySelector(selector);$el.onmousedown=hideModal});var $sliderOne=document.querySelector(\".slider.one\");var $sliderTwo=document.querySelector(\".slider.two\");$sliderOne.addEventListener(\"mousedown\",function(event){sliderListener(event,\".one\")});$sliderTwo.addEventListener(\"mousedown\",function(event){sliderListener(event,\".two\")});function hideModal(){$modal.setAttribute(\"style\",\"display: none\")}}function calculateImageMoveX(moveX,scaleX,imageWidth,maxMove){if(moveX/maxMove<=1/(1+scaleX)){return moveX*imageWidth/maxMove*scaleX}else{return imageWidth*scaleX/(1+scaleX)+(moveX-maxMove/(1+scaleX))*imageWidth/maxMove/scaleX}}function sliderListener(event,selector){var moveEnable=true;var mousedownFlag=false;var sliderInitOffset=0;var MIN_MOVE=0;var MAX_MOVE=260;var scaleX=%f;var moveX=0;var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);var imageWidth=$answer.offsetWidth-40;$sliderContainer.classList.add(\"sliderContainer_active\");sliderInitOffset=event.clientX;mousedownFlag=true;document.onmousemove=function(event){if(mousedownFlag==false){return}if(moveEnable==false){return}moveX=event.clientX-sliderInitOffset;moveX<MIN_MOVE?moveX=MIN_MOVE:moveX=moveX;moveX>MAX_MOVE?moveX=MAX_MOVE:moveX=moveX;var imageMoveX=calculateImageMoveX(moveX,scaleX,imageWidth,MAX_MOVE);$slider.setAttribute(\"style\",\"left:\"+moveX+\"px\");$sliderMask.setAttribute(\"style\",\"width:\"+moveX+\"px\");$answer.setAttribute(\"style\",\"left: -\"+imageMoveX+\"px\")};document.onmouseup=function(event){var imgMoveX=moveX*imageWidth/MAX_MOVE;sliderInitOffset=0;mousedownFlag=false;checkLocation(imgMoveX,selector);document.onmousemove=null;document.onmouseup=null}}function checkAllSuccess(){return checkSuccess(\".one\")&&checkSuccess(\".two\")}function checkSuccess(selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);return $sliderContainer.classList.contains(\"sliderContainer_success\")}function success(){console.log(\"success\");alert(\"success\");resetAll()}function resetAll(){reset(\".one\");reset(\".two\")}function reset(selector){var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);$sliderContainer.classList.remove(\"sliderContainer_success\");$sliderContainer.classList.remove(\"sliderContainer_fail\");$sliderContainer.classList.remove(\"sliderContainer_active\");$slider.setAttribute(\"style\",\"left:0px\");$sliderMask.setAttribute(\"style\",\"width:0px\");$answer.setAttribute(\"style\",\"left:0px\")}function checkLocation(move,selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);const Http=new XMLHttpRequest();const url=\"%s/checkLocation\";Http.open(\"POST\",url);Http.send(JSON.stringify({moveX:move,answerIndex:selector===\".one\"?10:1,}));Http.onreadystatechange=function(){if(Http.readyState===XMLHttpRequest.DONE&&Http.status===200){checkResult=JSON.parse(Http.response);if(checkResult){$sliderContainer.classList.add(\"sliderContainer_success\");$sliderContainer.removeEventListener(\"mousedown\",function(event){sliderListener(event,selector)});if(checkAllSuccess()){setTimeout(function(){success()},500)}}else{$sliderContainer.classList.add(\"sliderContainer_fail\");setTimeout(function(){resetAll()},500)}}}};";

    public static String JsCode = "onPageReady();function onPageReady(){var $btn=document.querySelector(\".btn\");var $modal=document.querySelector(\".slider-captcha\");$btn.onmousedown=function(){$modal.setAttribute(\"style\",\"display: block\")};[\".captcha-mask\"].forEach(function(selector){var $el=$modal.querySelector(selector);$el.onmousedown=hideModal});var $sliderOne=document.querySelector(\".slider.one\");var $sliderTwo=document.querySelector(\".slider.two\");$sliderOne.addEventListener(\"mousedown\",function(event){sliderListener(event,\".one\")});$sliderTwo.addEventListener(\"mousedown\",function(event){sliderListener(event,\".two\")});function hideModal(){$modal.setAttribute(\"style\",\"display: none\")}}function calculateImageMoveX(moveX,scaleX,imageWidth,maxMove){if(moveX/maxMove<=1/(1+scaleX)){return moveX*imageWidth/maxMove*scaleX}else{return imageWidth*scaleX/(1+scaleX)+(moveX-maxMove/(1+scaleX))*imageWidth/maxMove/scaleX}}function sliderListener(event,selector){var moveEnable=true;var mousedownFlag=false;var sliderInitOffset=0;var MIN_MOVE=0;var MAX_MOVE=260;var scaleX=%f;var moveX=0;var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);var imageWidth=$answer.offsetWidth-40;$sliderContainer.classList.add(\"sliderContainer_active\");sliderInitOffset=event.clientX;mousedownFlag=true;document.onmousemove=function(event){if(mousedownFlag==false){return}if(moveEnable==false){return}moveX=event.clientX-sliderInitOffset;moveX<MIN_MOVE?moveX=MIN_MOVE:moveX=moveX;moveX>MAX_MOVE?moveX=MAX_MOVE:moveX=moveX;var imageMoveX=calculateImageMoveX(moveX,scaleX,imageWidth,MAX_MOVE);$slider.setAttribute(\"style\",\"left:\"+moveX+\"px\");$sliderMask.setAttribute(\"style\",\"width:\"+moveX+\"px\");$answer.setAttribute(\"style\",\"left: -\"+imageMoveX+\"px\")};document.onmouseup=function(event){var imgMoveX=moveX*imageWidth/MAX_MOVE;sliderInitOffset=0;mousedownFlag=false;checkLocation(imgMoveX,selector);document.onmousemove=null;document.onmouseup=null}}function checkAllSuccess(){return checkSuccess(\".one\")&&checkSuccess(\".two\")}function checkSuccess(selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);return $sliderContainer.classList.contains(\"sliderContainer_success\")}function success(){console.log(\"success\");alert(\"success\");resetAll()}function resetAll(){reset(\".one\");reset(\".two\")}function reset(selector){var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);$sliderContainer.classList.remove(\"sliderContainer_success\");$sliderContainer.classList.remove(\"sliderContainer_fail\");$sliderContainer.classList.remove(\"sliderContainer_active\");$slider.setAttribute(\"style\",\"left:0px\");$sliderMask.setAttribute(\"style\",\"width:0px\");$answer.setAttribute(\"style\",\"left:0px\")}function checkLocation(move,selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);const Http=new XMLHttpRequest();const url=\"%s/checkLocation\";Http.open(\"POST\",url);Http.send(JSON.stringify({moveX:move,answerIndex:selector===\".one\"?10:1,}));Http.onreadystatechange=function(){if(Http.readyState===XMLHttpRequest.DONE&&Http.status===200){checkResult=JSON.parse(Http.response);if(checkResult){$sliderContainer.classList.add(\"sliderContainer_success\");$sliderContainer.removeEventListener(\"mousedown\",function(event){sliderListener(event,selector)});if(checkAllSuccess()){setTimeout(function(){success()},500)}}else{$sliderContainer.classList.add(\"sliderContainer_fail\");setTimeout(function(){resetAll()},500)}}}};";

    public static double scaleX = 1;

    private static String obfuscator(String filePath, String url, double scale) throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String result="";
        //TODO: node路径
        String cmd = "/Users/bytedance/.nvm/versions/node/v14.18.1/bin/node " + path + "static/js/obfuscator.js " + filePath + " " + url + " " + scale;

        Process ps = Runtime.getRuntime().exec(cmd);

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
        String line = null;
        while ((line = br.readLine()) != null) {
            result+=line+"\n";
        }

        br.close();
        ps.waitFor();

        return result;
    }

    private static String readToString(String filePath) throws IOException {
        File file = new File(filePath);
        Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];

        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();

        String fileContent = new String(filecontent);

        return fileContent;
    }

    private static void writeToFile(String filePath, String fileContent) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file.getName()));
        out.write(fileContent);
        out.close();
    }

    public static void changeJsScale() throws IOException, InterruptedException {
        double randomScale = Math.random() * 1.5 + 0.5;
        scaleX = randomScale;
    }

    public static String getJsSource() throws IOException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String jsFileTemplate = readToString(path + "templates/captcha.js.template");
        jsFileTemplate = jsFileTemplate.replace("{0}", "1");
        jsFileTemplate = jsFileTemplate.replace("{1}", "http://localhost:8080");
        return jsFileTemplate;
    }

    public static String getJsCode(String url, double scaleX) throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        return obfuscator(path + "templates/captcha.js.template", url, scaleX);
    }

    public static String getHtmlCode(String questionImg, String answer1Img, String answer2Img, String JsCode) throws IOException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String htmlFileTemplate = readToString(path + "templates/index.html.template");
        String htmlFileSource = String.format(htmlFileTemplate, questionImg, answer1Img, answer2Img, JsCode);
        return htmlFileSource;
    }
}
