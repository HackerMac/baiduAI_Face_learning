package com.baidu;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @Classname FaceTest
 * @Description TODO
 * @Date 2021/7/11 21:00
 * @Created by MINGKU
 */
public class FaceTest {
    private AipFace client;

    @Before
    public void init() {
        client = new AipFace("24530283", "API Key", "Secret Key");
    }

    // 人脸注册
    @Test
    public void testFaceRegister() throws IOException {
        // 1. 创建Java 代码和百度云交互的client对象
        //AipFace client = new AipFace("24530283", "rplPhR9MstIpAIGfLw9v7pWo", "liTBGrnvvwd9ln3C6QCIVTnGKrkS1dl4");
        //2. 参数设置
        HashMap<String, String> options = new HashMap<>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        //3. 构造图片
        String path = "C:\\Users\\S2\\Desktop\\项目\\辅助\\测试\\img\\2.jpg";
        // 图片格式 两种格式: url地址形式 Base64字符串形式
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        //4. 调用api方法完成人脸注册
        /**
         * image 图片的url或者图片的base64字符串
         * imageType 图片形式
         * 组ID (固定字符串)
         * 用户ID
         * HashMap 的基本参数配置
         */
        JSONObject res = client.addUser(image, "BASE64", "1000", "1000", options);
        System.out.println(res.toString());
        /**
         * 运行结果：
         *  0 [main] INFO com.baidu.aip.client.BaseClient  - get access_token success. current state: STATE_AIP_AUTH_OK
         * 1 [main] DEBUG com.baidu.aip.client.BaseClient  - current state after check priviledge: STATE_TRUE_AIP_USER
         * {"result":null,"log_id":9989201101001,"error_msg":"face already exist","cached":0,"error_code":223105,"timestamp":1626010050}
         */
    }

    /**
     * 人脸检测 判断图片中是否有人脸信息
     */
    @Test
    public void testFaceCheck() throws IOException {
        //2. 构造图片
        String path = "C:\\Users\\S2\\Desktop\\项目\\辅助\\测试\\img\\ac.jpg";
        // 图片格式 两种格式: url地址形式 Base64字符串形式
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        //4. 调用api方法完成人脸检测
        /**
         * image 图片的url或者图片的base64字符串
         * imageType 图片形式
         * HashMap 的基本参数配置 可以使用null 默认配置
         */
        JSONObject res = client.detect(image, "BASE64", null);
        System.out.println(res.toString());
        /**
         * 运行结果：
         * 0 [main] INFO com.baidu.aip.client.BaseClient  - get access_token success. current state: STATE_AIP_AUTH_OK
         * 1 [main] DEBUG com.baidu.aip.client.BaseClient  - current state after check priviledge: STATE_TRUE_AIP_USER
         * {"result":null,"log_id":1584201455555,"error_msg":"pic not has face","cached":0,"error_code":222202,"timestamp":1626010008}
         */
    }

    /**
     * 人脸更新 更新人脸库的图片
     */
    @Test
    public void testFaceUpdate() throws IOException {
        //2. 参数设置
        HashMap<String, String> options = new HashMap<>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        //3. 构造图片
        String path = "C:\\Users\\S2\\Desktop\\项目\\辅助\\测试\\img\\5.jpg";
        // 图片格式 两种格式: url地址形式 Base64字符串形式
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        //4. 调用api方法完成人脸注册
        /**
         * image 图片的url或者图片的base64字符串
         * imageType 图片形式
         * 组ID (固定字符串)
         * 用户ID
         * HashMap 的基本参数配置
         */
        JSONObject res = client.updateUser(image, "BASE64", "1000", "1000", options);
        System.out.println(res.toString());
        /**
         * {"result":{"face_token":"e90a1c9f3c5314e7199dbdc5bad33af3","location":{"top":309.41,"left":549.67,"rotation":0,"width":224,"height":233}},"log_id":2575891594254,"error_msg":"SUCCESS","cached":0,"error_code":0,"timestamp":1626010725}
         */
    }

    /**
     * 人脸搜索， 检测当前人脸库中是否有保存当前照片, 获取相识度最高或叫评分最高的图片
     */
    @Test
    public void testFaceSearch() throws IOException {
        //构造图片
        String path = "C:\\Users\\S2\\Desktop\\项目\\辅助\\测试\\img\\1.jpg";
        // 图片格式 两种格式: url地址形式 Base64字符串形式
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        //人脸搜索
        JSONObject res = client.search(image, "BASE64", "1000", null);
        System.out.println(res.toString(2));
        /**
         * {
         *   "result": {
         *     "face_token": "d95a21ac56345acc7774c40bbdf72ec5",
         *     "user_list": [{
         *       "score": 100,
         *       "group_id": "1000",
         *       "user_id": "1000",
         *       "user_info": ""
         *     }]
         *   },
         *   "log_id": 8465101992013,
         *   "error_msg": "SUCCESS",
         *   "cached": 0,
         *   "error_code": 0,
         *   "timestamp": 1626010422
         * }
         */
    }
}
