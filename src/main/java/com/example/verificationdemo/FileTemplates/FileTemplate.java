package com.example.verificationdemo.FileTemplates;

import org.springframework.util.ClassUtils;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.Charset;

public class FileTemplate {
    public static String htmlFile = "<!DOCUMENT html><html><head><title>captcha</title><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><script src=\"static/js/jquery.js\"></script><link href=\"static/css/font-awesome.min.css\" rel=\"stylesheet\"><link href=\"static/css/captcha.css\" rel=\"stylesheet\"></head><body><button class=\"btn\">安全验证</button><div class=\"slider-captcha\"><div class=\"captcha-mask\"></div><div class=\"captcha-body\"><div class=\"card\"><div class=\"card-header\"><span>请完成安全验证!</span></div><div class=\"card-body\"><div id=\"captcha\"><div class=\"questionDiv\"><img class=\"questionImg\" src=\"data:image/jpg;base64,%s\"/><img class=\"questionImg\" src=\"data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7\"/></div><div class=\"answerOneDiv\"><img class=\"answerImg one\" src=\"data:image/jpg;base64,%s\"/></div><div class=\"answerTwoDiv\"><img class=\"answerImg two\" src=\"data:image/jpg;base64,%s\"/></div><div class=\"sliderContainer one\"><div class=\"sliderBg one\"></div><div class=\"sliderMask one\"><div class=\"slider one\"><i class=\"sliderIcon fa fa-arrow-right\"></i></div></div></div><div class=\"sliderContainer two\"><div class=\"sliderBg two\"></div><div class=\"sliderMask two\"><div class=\"slider two\"><i class=\"sliderIcon fa fa-arrow-right\"></i></div></div></div></div></div></div></div></div><script>%s</script></body></html>";

    public static String jsFile = "onPageReady();function onPageReady(){var $btn=document.querySelector(\".btn\");var $modal=document.querySelector(\".slider-captcha\");$btn.onmousedown=function(){$modal.setAttribute(\"style\",\"display: block\")};[\".captcha-mask\"].forEach(function(selector){var $el=$modal.querySelector(selector);$el.onmousedown=hideModal});var $sliderOne=document.querySelector(\".slider.one\");var $sliderTwo=document.querySelector(\".slider.two\");$sliderOne.addEventListener(\"mousedown\",function(event){sliderListener(event,\".one\")});$sliderTwo.addEventListener(\"mousedown\",function(event){sliderListener(event,\".two\")});function hideModal(){$modal.setAttribute(\"style\",\"display: none\")}}function calculateImageMoveX(moveX,scaleX,imageWidth,maxMove){if(moveX/maxMove<=1/(1+scaleX)){return moveX*imageWidth/maxMove*scaleX}else{return imageWidth*scaleX/(1+scaleX)+(moveX-maxMove/(1+scaleX))*imageWidth/maxMove/scaleX}}function sliderListener(event,selector){var moveEnable=true;var mousedownFlag=false;var sliderInitOffset=0;var MIN_MOVE=0;var MAX_MOVE=260;var scaleX=%f;var moveX=0;var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);var imageWidth=$answer.offsetWidth-40;$sliderContainer.classList.add(\"sliderContainer_active\");sliderInitOffset=event.clientX;mousedownFlag=true;document.onmousemove=function(event){if(mousedownFlag==false){return}if(moveEnable==false){return}moveX=event.clientX-sliderInitOffset;moveX<MIN_MOVE?moveX=MIN_MOVE:moveX=moveX;moveX>MAX_MOVE?moveX=MAX_MOVE:moveX=moveX;var imageMoveX=calculateImageMoveX(moveX,scaleX,imageWidth,MAX_MOVE);$slider.setAttribute(\"style\",\"left:\"+moveX+\"px\");$sliderMask.setAttribute(\"style\",\"width:\"+moveX+\"px\");$answer.setAttribute(\"style\",\"left: -\"+imageMoveX+\"px\")};document.onmouseup=function(event){var imgMoveX=moveX*imageWidth/MAX_MOVE;sliderInitOffset=0;mousedownFlag=false;checkLocation(imgMoveX,selector);document.onmousemove=null;document.onmouseup=null}}function checkAllSuccess(){return checkSuccess(\".one\")&&checkSuccess(\".two\")}function checkSuccess(selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);return $sliderContainer.classList.contains(\"sliderContainer_success\")}function success(){console.log(\"success\");alert(\"success\");resetAll()}function resetAll(){reset(\".one\");reset(\".two\")}function reset(selector){var $slider=document.querySelector(\".slider\"+selector);var $sliderMask=document.querySelector(\".sliderMask\"+selector);var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);var $answer=document.querySelector(\".answerImg\"+selector);$sliderContainer.classList.remove(\"sliderContainer_success\");$sliderContainer.classList.remove(\"sliderContainer_fail\");$sliderContainer.classList.remove(\"sliderContainer_active\");$slider.setAttribute(\"style\",\"left:0px\");$sliderMask.setAttribute(\"style\",\"width:0px\");$answer.setAttribute(\"style\",\"left:0px\")}function checkLocation(move,selector){var $sliderContainer=document.querySelector(\".sliderContainer\"+selector);const Http=new XMLHttpRequest();const url=\"%s/checkLocation\";Http.open(\"POST\",url);Http.send(JSON.stringify({moveX:move,answerIndex:selector===\".one\"?10:1,}));Http.onreadystatechange=function(){if(Http.readyState===XMLHttpRequest.DONE&&Http.status===200){checkResult=JSON.parse(Http.response);if(checkResult){$sliderContainer.classList.add(\"sliderContainer_success\");$sliderContainer.removeEventListener(\"mousedown\",function(event){sliderListener(event,selector)});if(checkAllSuccess()){setTimeout(function(){success()},500)}}else{$sliderContainer.classList.add(\"sliderContainer_fail\");setTimeout(function(){resetAll()},500)}}}};";

    public static double scaleX = 1;

    private static File getRandomFile(String src){
        File folder = new File(src);
        File[] tempList = folder.listFiles();

        int randomIndex = (int)(Math.random() * tempList.length);
        File randomFile = tempList[randomIndex];
        return randomFile;
    }
    private static String getFileName(File file){
        String name = file.getName();
        return name.substring(0, name.lastIndexOf('.'));
    }

    private static int getQuestionAnswer(String question){
        String[] operands = question.split("=|\\+");
        return Integer.parseInt(operands[0]) + Integer.parseInt(operands[1]);
    }

    private static String getBase64File(File file) throws IOException {
        InputStream randomFileIS = new FileInputStream(file);
        byte[] data = new byte[randomFileIS.available()];
        randomFileIS.read(data);
        randomFileIS.close();
        return new BASE64Encoder().encode(data);
    }

    private static String obfuscator(String filePath) throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String result="";
        String cmd = "/Users/bytedance/.nvm/versions/node/v14.18.1/bin/node " + path + "static/js/obfuscator.js " + filePath;

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

    public static void changeFile() throws IOException, InterruptedException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String htmlFileTemplate = readToString("./index.html.template");
        String jsFileTemplate = readToString("./captcha.js.template");

        File question = getRandomFile(path + "static/image/Question");
        File answer1 = getRandomFile(path + "static/image/Answer");
        File answer2 = getRandomFile(path + "static/image/Answer");

        String questionString = getFileName(question);
        int answerInteger = getQuestionAnswer(questionString);

        double randomScale = Math.random() * 1.5 + 0.5;
        String url = "http://localhost:8080";

        String jsFileSource = String.format(jsFileTemplate, randomScale, url);
        writeToFile("./captcha.js", jsFileSource);

        String jsCode = obfuscator(path + "");


        String htmlFileSource = String.format(htmlFileTemplate, getBase64File(question), getBase64File(answer1), getBase64File(answer2), jsCode);
        writeToFile("./index.html", htmlFileSource);

        htmlCode = htmlFileSource;
        scaleX = randomScale;
    }

    public static String readToString(String filePath) throws IOException {
        File file = new File(filePath);
        Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];

        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();

        String fileContent = new String(filecontent);

        return fileContent;
    }

    public static void writeToFile(String filePath, String fileContent) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file.getName()));
        out.write(fileContent);
        out.close();
    }
}
