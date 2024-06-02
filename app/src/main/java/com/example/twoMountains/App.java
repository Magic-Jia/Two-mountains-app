package com.example.twoMountains;

import android.app.Application;
import android.content.Context;

import com.example.twoMountains.androidble.ble.BLEManager;
import com.example.twoMountains.bean.AdministratorBean;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.CigaretteDataBean;
import com.example.twoMountains.bean.FMBean;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.PreferenceUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class App extends Application {
    private static Context context;
    public static UserBean user;
    public static BLEManager bleManager;
    public static boolean bleConnectState = false;
    public static IWXAPI wxApi;

    public void onCreate() {
        super.onCreate();
        context = this;

        DBCreator.init();
        String account = PreferenceUtil.getInstance().get("logger", "");
        if (!account.isEmpty()) {
            user = DBCreator.getUserDao().queryUserByAccount(account);
        }
        boolean firstIn = PreferenceUtil.getInstance().get("firstIn", true);
        if (firstIn) {
            PreferenceUtil.getInstance().save("firstIn", false);
            AppInit();
        }
        // 初始化微信SDK
        wxApi = WXAPIFactory.createWXAPI(this, "wx68c3d9bac60154ed", true);
        wxApi.registerApp("wx68c3d9bac60154ed");

        /*DBCreator.getCigaretteDataDao().deleteCigaretteDataById(1);
        DBCreator.getCigaretteDataDao().deleteCigaretteDataById(2);*/
        /*for(int i=7;i<=12;i++){
            deleteArticle(i);
        }*/
        /*for(int i=10;i<=12;i++){
            deleteFm(i);
        }*/

        /*deleteAdministrator(2);*/

        /*for(int i=0;i<=12;i++){
            addCigaretteData(1,new SimpleDateFormat("yyyy-MM-dd").format());
        }*/

    }

    public static void AppInit(){
        addArticle("How to Keep Your Motivation High?","Introduction: Maintaining high levels of motivation is essential for achieving success in any endeavor. However, staying motivated can be challenging, especially when faced with obstacles and setbacks. In this article, we will explore effective strategies to keep your motivation high and stay focused on your goals.\n\n1.Set Clear Goals: One of the key factors in staying motivated is having clear and specific goals. Define what you want to achieve and break it down into smaller, manageable tasks. Setting achievable milestones can help you track your progress and stay motivated along the way.\n\n2.Find Your Why: Understanding the reasons behind your goals can provide a powerful source of motivation. Reflect on why your goals are important to you and how they align with your values and aspirations. Connecting with your deeper purpose can fuel your motivation during challenging times.\n\n3.Create a Positive Environment: Surround yourself with positivity and inspiration. Seek out supportive and encouraging individuals who believe in your goals. Create a workspace that is conducive to productivity and motivation, filled with motivational quotes, images, or objects that inspire you.\n\n4.Celebrate Small Wins: Acknowledge and celebrate your achievements, no matter how small. Recognizing your progress and accomplishments can boost your confidence and motivation to keep moving forward. Take time to appreciate the effort you have put in and the steps you have taken towards your goals.\n\n5.Stay Flexible and Adapt: Be open to adjusting your plans and strategies as needed. Life is full of unexpected challenges and changes, and being adaptable can help you navigate obstacles without losing motivation. Embrace setbacks as learning opportunities and use them to grow stronger.\n\n6.Practice Self-Care: Taking care of your physical and mental well-being is crucial for maintaining motivation. Make time for activities that recharge you, such as exercise, meditation, hobbies, or spending time with loved ones. Prioritize self-care to ensure you have the energy and resilience to stay motivated.\n\n7.Stay Inspired: Seek inspiration from successful individuals, motivational books, podcasts, or videos. Learning from others' experiences and achievements can reignite your motivation and provide fresh perspectives on your goals. Stay curious, continue learning, and stay inspired on your journey.\n\nConclusion: Keeping your motivation high requires effort, commitment, and a positive mindset. By setting clear goals, finding your why, creating a supportive environment, celebrating wins, staying flexible, practicing self-care, and seeking inspiration, you can maintain a high level of motivation and stay on track towards achieving your aspirations. Remember that motivation is a journey, and it's okay to have ups and downs along the way. Stay focused, stay resilient, and keep your motivation burning bright.");
        addArticle("The Benefits of Quitting Smoking.","Smoking is a harmful habit that can have serious consequences on your health. However, making the decision to quit smoking can have a profound impact on your well-being and overall quality of life. Here are some of the benefits of quitting smoking:\n\n1.Improved Lung Health: Smoking damages the lungs and can lead to respiratory problems such as chronic bronchitis and emphysema. By quitting smoking, you can improve your lung function and reduce the risk of developing these conditions.\n\n2.Reduced Risk of Cancer: Smoking is a leading cause of various types of cancer, including lung, throat, and mouth cancer. Quitting smoking can significantly lower your risk of developing these life-threatening diseases.\n\n3.Better Cardiovascular Health: Smoking increases the risk of heart disease and stroke by damaging the blood vessels and increasing blood pressure. When you quit smoking, you reduce the strain on your heart and improve your cardiovascular health.\n\n4.Improved Sense of Taste and Smell: Smoking can dull your sense of taste and smell over time. By quitting smoking, you can regain these senses and enjoy the flavors and aromas of food and beverages.\n\n5.Enhanced Energy Levels: Smoking can cause fatigue and reduce energy levels due to the impact on oxygen levels in the body. Quitting smoking can lead to increased energy and vitality.\n\n6.Better Skin Health: Smoking accelerates skin aging and can lead to wrinkles and dull complexion. When you quit smoking, you can improve the health and appearance of your skin.\n\n7.Financial Savings: Smoking is an expensive habit that can drain your finances. By quitting smoking, you can save money that would have been spent on cigarettes and related health care costs.\n\nQuitting smoking is a challenging journey, but the benefits of a smoke-free life are well worth the effort. If you are considering quitting smoking, remember that there is support available to help you along the way. Your health and well-being will thank you for making the decision to quit smoking.");
        addArticle("Tips for Quitting Smoking Successfully.","Quitting smoking is a challenging but rewarding journey that can have a positive impact on your health and well-being. If you are ready to take the step towards a smoke-free life, here are some helpful tips to guide you through the process:\n\n1.Set a Quit Date: Choose a specific date to quit smoking and mark it on your calendar. Having a clear goal in mind can help you stay motivated and committed to your decision.\n\n2.Identify Triggers: Pay attention to situations, emotions, or activities that trigger your smoking habit. By identifying these triggers, you can develop strategies to avoid or cope with them without reaching for a cigarette.\n\n3.Seek Support: Inform your friends, family, and colleagues about your decision to quit smoking. Their support and encouragement can make a significant difference in your journey towards a smoke-free life.\n\n4.Consider Nicotine Replacement Therapy: Nicotine replacement therapy, such as nicotine patches or gum, can help reduce withdrawal symptoms and cravings associated with quitting smoking. Consult with a healthcare provider to determine the best option for you.\n\n5.Stay Active: Engage in physical activities or hobbies that can distract you from the urge to smoke. Exercise can also help reduce stress and improve your overall well-being.\n\n6.Practice Relaxation Techniques: Explore relaxation techniques such as deep breathing, meditation, or yoga to manage stress and cravings during the quitting process.\n\n7.Reward Yourself: Celebrate small milestones and achievements along the way. Treat yourself to something special or indulge in activities that bring you joy as a reward for your progress.\n\n8.Stay Positive: Quitting smoking is a journey with ups and downs. Stay positive and remind yourself of the reasons why you decided to quit in the first place. Focus on the benefits of a smoke-free life.\n\n9.Prepare for Challenges: Be prepared for challenges and setbacks during the quitting process. Remember that slip-ups are a natural part of the journey, and it's important to stay resilient and committed to your goal.\n\n10.Stay Persistent: Quitting smoking may not be easy, but it is possible with determination and perseverance. Stay persistent and believe in your ability to lead a healthier, smoke-free life.\n\nRemember, you are not alone in your journey to quit smoking. Reach out for support, stay committed to your goal, and celebrate each step towards a healthier, smoke-free you.");
        addArticle("Breaking Free: The Journey to Quit Smoking.","Quitting smoking is a transformative journey that requires determination, resilience, and a commitment to better health. The decision to break free from the grip of cigarettes marks the beginning of a profound personal transformation. It is a journey filled with challenges, triumphs, and self-discovery.\n\nThe first step in the journey to quit smoking is acknowledging the harmful effects of smoking on your health and well-being. Understanding the risks associated with smoking can serve as a powerful motivator to embark on the path to a smoke-free life. It is a decision that stems from self-care and a desire for a healthier future.\n\nAs you navigate the journey to quit smoking, you may encounter various obstacles along the way. Nicotine cravings, withdrawal symptoms, and triggers that tempt you to reach for a cigarette can test your resolve. However, with each challenge overcome, you grow stronger and more determined to stay on course.\n\nSeeking support from friends, family, or support groups can provide encouragement and accountability during the quitting process. Sharing your struggles and successes with others who understand the journey can offer valuable insights and motivation to stay committed to your goal.\n\nThe journey to quit smoking is not just about breaking a habit; it is about reclaiming control over your life and health. It is a journey of self-discovery, self-improvement, and empowerment. With each smoke-free day, you take a step closer to a brighter, healthier future.\n\nCelebrate each milestone, no matter how small, as a testament to your strength and resilience. Recognize the progress you have made and the positive changes that quitting smoking has brought into your life. Embrace the journey as an opportunity for growth and transformation.\n\nBreaking free from the grip of smoking is a courageous and empowering choice. It is a journey of self-liberation, renewal, and empowerment. As you navigate the ups and downs of quitting smoking, remember that you are embarking on a journey towards a healthier, smoke-free life. Stay strong, stay committed, and embrace the journey to break free from smoking.");
        insertFm1();
        insertFm2();
        insertFm3();
        addAdministrator();
    }

    public static void addCigaretteData(int userId,String date,float motivation,int cigaretteQuantity,int vapeQuantity){
        CigaretteDataBean cigaretteDataBean = new CigaretteDataBean();
        cigaretteDataBean.userId = userId;
        cigaretteDataBean.date = date;
        cigaretteDataBean.motivation = motivation;
        cigaretteDataBean.cigaretteQuantity = cigaretteQuantity;
        cigaretteDataBean.vapeQuantity = vapeQuantity;
        DBCreator.getCigaretteDataDao().addCigaretteData(cigaretteDataBean);
    }

    public static void addUser(){
        UserBean userBean = new UserBean();
        userBean.account = "15210669565";
        userBean.password = "123456";
        DBCreator.getUserDao().registerUser(userBean);
    }

    public static void deleteUser(int id){
        DBCreator.getUserDao().deleteUserById(id);
    }

    public static void addAdministrator(){
        AdministratorBean administratorBean = new AdministratorBean();
        administratorBean.account = "15210669565";
        administratorBean.password = "123456";
        DBCreator.getAdministratorDao().registerAdministrator(administratorBean);
    }

    public static void deleteAdministrator(int id){
        DBCreator.getAdministratorDao().deleteAdministratorById(id);
    }

    private static void insertFm2() {

        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "Seraphina Everleigh";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/03/05/f928f04b56c94d1ca927c4bb4bcf293e!400x400.jpeg";
        fmBean.fmTitle = "Summer and early spring | The distance between you and me is sometimes far and sometimes close";
        fmBean.fmSecTitle = "\"Courage is not the absence of fear, but the ability to continue moving forward in the face of fear.\"";
        DBCreator.getFMDao().insert(fmBean);
    }

    private static void insertFm3() {
        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 2;
        fmBean.upId = 2;
        fmBean.fmAuthor = "Maximilian Thornebridge";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/02/26/60cbe32a7e45460db52db562898ca0c0!400x400.jpeg";
        fmBean.fmTitle = "Psychological regulation can be achieved through meditation, exercise, social support, and other means.";
        fmBean.fmSecTitle = "";
        DBCreator.getFMDao().insert(fmBean);
    }

    private static void insertFm1() {

        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "Arabella Rosalind";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/03/02/76a54ee2439a4ff4ba70d80955f0f845!400x400.jpeg";
        fmBean.fmTitle = "Love yourself, happening in the depths of tranquility | Xiaosheng Listening";
        fmBean.fmSecTitle = "You can be very close to yourself, go through all emotions, those judgments of yourself, the way others treat you, and then reach yourself. You can wait for the night to fall, adapt to the arrival of darkness, and in the depths of tranquility, listen to the slightest voice in your heart. Your eyes will light up the night, your heart will burn flames, and in the depths of tranquility, you will respond to yourself. Even if it's just waiting, hold your hand tightly and know that dawn will surely come. In the depths of tranquility, you are sincere with yourself. In the depths of tranquility, love is happening.";
        DBCreator.getFMDao().insert(fmBean);
    }

    public static void deleteFm(int id){
        DBCreator.getFMDao().deleteFMByFMId(id);
    }

    public static void addArticle(String title,String content){
        ArticleBean articleBean = new ArticleBean();
        articleBean.title = title;
        articleBean.content = content;
        DBCreator.getArticleDao().addArticle(articleBean);
    }

    public static void deleteArticle(int id){
        DBCreator.getArticleDao().deleteArticleById(id);
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isLogin() {
        return user != null;
    }

    public static void logout() {
        PreferenceUtil.getInstance().remove("logger");
        user = null;
    }
}
