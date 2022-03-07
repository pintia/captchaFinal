package com.example.verificationdemo.FileTemplates;

import com.example.verificationdemo.type.JsFileResource;
import org.springframework.util.ClassUtils;
import java.io.*;
import java.nio.charset.Charset;

public class FileTemplate {
    private static String JsCode = "onPageReady();function onPageReady(){var $btn=document.querySelector(\".btn\");var $modal=document.querySelector(\".slider-captcha\");$btn.onmousedown=function(){$modal.setAttribute(\"style\",\"display: block\")};[\".captcha-mask\"].forEach(function(selector){var $el=$modal.querySelector(selector);$el.onmousedown=hideModal});var $sliderOne=document.querySelector(\".slider.one\");var $sliderTwo=document.querySelector(\".slider.two\");$sliderOne.addEventListener(\"mousedown\",function(event){sliderListener(event,\".one\")});$sliderTwo.addEventListener(\"mousedown\",function(event){sliderListener(event,\".two\")});function hideModal(){$modal.setAttribute(\"style\",\"display: none\")}}function calculateImageMoveX(moveX,scaleX,imageWidth,maxMove){if(moveX/maxMove<=1/(1+scaleX)){return moveX*imageWidth/maxMove*scaleX}else{return imageWidth*scaleX/(1+scaleX)+(moveX-maxMove/(1+scaleX))*imageWidth/maxMove/scaleX}}function sliderListener(event,selector){var moveEnable=true;var mousedownFlag=false;var sliderInitOffset=0;var MIN_MOVE=0;var MAX_MOVE=260;var scaleX=1;var moveX=0;var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);var imageWidth=$answer.offsetWidth-40;$sliderContainer.classList.add(\"sliderContainer_active\");sliderInitOffset=event.clientX;mousedownFlag=true;document.onmousemove=function(event){if(mousedownFlag==false){return}if(moveEnable==false){return}moveX=event.clientX-sliderInitOffset;moveX<MIN_MOVE?moveX=MIN_MOVE:moveX=moveX;moveX>MAX_MOVE?moveX=MAX_MOVE:moveX=moveX;var imageMoveX=calculateImageMoveX(moveX,scaleX,imageWidth,MAX_MOVE);$slider.setAttribute(\"style\",\"left:\"+moveX+\"px\");$sliderMask.setAttribute(\"style\",\"width:\"+moveX+\"px\");$answer.setAttribute(\"style\",\"left: -\"+imageMoveX+\"px\")};document.onmouseup=function(event){var imgMoveX=moveX*imageWidth/MAX_MOVE;sliderInitOffset=0;mousedownFlag=false;checkLocation(imgMoveX,selector);document.onmousemove=null;document.onmouseup=null}}function checkAllSuccess(){return checkSuccess(\".one\")&&checkSuccess(\".two\")}function checkSuccess(selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);return $sliderContainer.classList.contains(\"sliderContainer_success\")}function success(){console.log(\"success\");alert(\"success\");resetAll()}function resetAll(){reset(\".one\");reset(\".two\")}function reset(selector){var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);$sliderContainer.classList.remove(\"sliderContainer_success\");$sliderContainer.classList.remove(\"sliderContainer_fail\");$sliderContainer.classList.remove(\"sliderContainer_active\");$slider.setAttribute(\"style\",\"left:0px\");$sliderMask.setAttribute(\"style\",\"width:0px\");$answer.setAttribute(\"style\",\"left:0px\")}function checkLocation(move,selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);const Http=new XMLHttpRequest();const url=\"http://localhost:8080/checkLocation\";Http.open(\"POST\",url);Http.send(JSON.stringify({moveX:move,answerIndex:selector===\".one\"?10:1,}));Http.onreadystatechange=function(){if(Http.readyState===XMLHttpRequest.DONE&&Http.status===200){checkResult=JSON.parse(Http.response);if(checkResult){$sliderContainer.classList.add(\"sliderContainer_success\");$sliderContainer.removeEventListener(\"mousedown\",function(event){sliderListener(event,selector)});if(checkAllSuccess()){setTimeout(function(){success()},500)}}else{$sliderContainer.classList.add(\"sliderContainer_fail\");setTimeout(function(){resetAll()},500)}}}};";

    private static double scaleX = 1;

    public static JsFileResource jsFileResource = new JsFileResource(scaleX, JsCode);

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
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String url = "http://localhost:8080";
        String jsCode = obfuscator(path + "templates/captcha.js.template", url, randomScale);
        jsFileResource = new JsFileResource(randomScale, jsCode);
    }

    public static String getJsSource() throws IOException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String jsFileTemplate = readToString(path + "templates/captcha.js.template");
        jsFileTemplate = jsFileTemplate.replace("{0}", "1");
        jsFileTemplate = jsFileTemplate.replace("{1}", "http://localhost:8080");
        return jsFileTemplate;
    }

    private static String getJsCode(String url, double scaleX) throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        return obfuscator(path + "templates/captcha.js.template", url, scaleX);
    }

    public static String getHtmlCode(String questionImg, String answer1Img, String JsCode) throws IOException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String htmlFileTemplate = readToString(path + "templates/captcha.html.template");
        String htmlFileSource = String.format(htmlFileTemplate, questionImg, answer1Img, JsCode);
        return htmlFileSource;
    }
}
