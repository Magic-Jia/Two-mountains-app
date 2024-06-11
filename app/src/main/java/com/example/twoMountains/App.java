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


        /*for(int i=1;i<=40;i++){
            deleteArticle(i);
        }*/
        /*DBCreator.getCigaretteDataDao().deleteCigaretteDataById(1);
        DBCreator.getCigaretteDataDao().deleteCigaretteDataById(2);*/

        /*for(int i=10;i<=12;i++){
            deleteFm(i);
        }*/

        /*deleteAdministrator(2);*/

        /*for(int i=0;i<=14;i++){
            addCigaretteData(1,"2024-05-01",80,40,0);
        }*/

        /*for(int i=51;i<=68;i++){
            DBCreator.getCigaretteDataDao().deleteCigaretteDataById(i);
        }*/


    }

    public static void AppInit(){
        addArticle("Day 1 - Get used to your new vape!","The Two Mountains X-pedition 1 vape unit has been purposefully designed to help you to quit smoking. It only works if you use it. The more you vape, the less you smoke.\n" +
                "\n" +
                "If you are intent on quitting smoking, then over the next few days (3-6 days) use the X-pedition1 vape more frequently, and reduce your cigarette consumption by 1 for each time you use the vape.  1 vape use = 1 cigarette\n" +
                "\n" +
                "We suggest that you use your vape 1 or 2 times more each successive day until you reach your target and reduce your consumption of cigarettes by 1 or 2 each day.\n" +
                "\n" +
                "We want you to get from smoking and not vaping to vaping and smoking equal amounts.\n" +
                "\n" +
                "For Example, if you are a pack a day smoker:\n" +
                "\n" +
                "Smoking 20\t\t\t\tSmoking 10\n" +
                "\t\t\t\tto \t\t\t\t\t\t\t\t\t(Add Graphic1)\n" +
                "Vaping 0\t\t\t\tVaping 10\n" +
                "\n" +
                "This should take about five days. The APP will be monitoring your vape usage and asking you daily, \"How many cigarettes did you smoke yesterday?\n" +
                "\n" +
                "When you meet this first goal, smoking and vaping equally, then you can move on.\n" +
                "\n" +
                "During these next few days you should be preparing yourself for the next step, \n" +
                "going from equal vaping and smoking to only vaping and not smoking.\n" +
                "\n" +
                "Vaping 10\t\t\t\tVaping 20\n" +
                "\t\t\t\tto\t\t\t\t\t\t\t\t\t(Add Graphic2)\n" +
                "Smoking 10\t\t\t\tSmoking 0\n" +
                "\n" +
                "This is mental preparation, and we will start teaching this to you tomorrow.\n");
        addArticle("Day 2 - Preparing to Quit","How determined are you to quit smoking?\n" +
                "\n" +
                "This is the other question that we ask you daily.\n" +
                "\n" +
                "How many cigarettes did you smoke yesterday? \tX cigarettes\n" +
                "and\t\t\t\t\t\t\t\t\t(Add Screen Shots1+2)\n" +
                "How determined are you to quit smoking? \t\t\tX % determined\n" +
                "\n" +
                "Our job is to teach you and encourage you to be very determined to quit smoking.\n" +
                "\n" +
                "The Two Mountains Stop Smoking APP will graph your vaping uses and graph your answers to these two daily questions.\n" +
                "\n" +
                "(Insert Screen Shot3, example of Two Mountains Stop Smoking APP graph)\n" +
                "Top line \t\t% Determined to Quit 70% \n" +
                "(70-100% Green line, 60% Yellow line, 0-50% Red line)\n" +
                "\n" +
                "Middle line \t\t# of Cigarettes Smoked Yesterday (Black line)\n" +
                "\n" +
                "Bottom Line\t\t# of Vape Uses (Light or medium blue line, contrasting colour)\n" +
                "\n" +
                "\n" +
                "Now you can answer the two daily questions and view your graph.\n" +
                "\n" +
                "Tomorrow we can see if you are making progress, and we will begin to tell you about the Two Mountains stop-smoking method.\n");
        addArticle("Day 3 - Our Stop-Smoking Method","In the introduction we wrote, \"Using psychology and technology, we are breaking your smoking habit into two easier-to-manage challenges. First, we will teach you to break the smoking habit. Second, we will teach you to beat the nicotine dependency. You can learn how to plan and prepare to summit these two mountains successfully.\"\n" +
                "\n" +
                "The first mountain you need to climb is the stop-smoking phase.\n" +
                "The second mountain you need to climb is the quit-nicotine phase.\n" +
                "\n" +
                "You are transitioning from smoking to vaping, the beginning of the stop-smoking phase. The X-pedition1 vape provides you with nicotine equal to your cigarettes. The X-pedition1 is a substitute product for your cigarettes.  1 use = 1 cigarette\n" +
                "\n" +
                "If you try to quit smoking without help then you are only 8% likely to succeed.\n" +
                "If you try to quit smoking with using nicotine gum or patches then you are 12% likely to succeed. Substituting nicotine can help you to quit smoking, but it is not enough!\n" +
                "\n" +
                "Some government sponsored stop-smoking campaigns claim a 20% success rate. Some psychologists and stop-smoking centers claim a 60% success rate. These programs are more successful because they provide support and encouragement to people trying to quit smoking. But they are expensive.\n" +
                "\n" +
                "The Two Mountains Stop-Smoking Program teaches, supports and encourages people to quit smoking through the APP, using the latest psychological information available, and the X-pedition 1 vape unit provides the nicotine that smokers say that they need. We break the smoking habit into two manageable parts. Climbing the first mountain involves transitioning to vaping and counting 90 smoke-free days. Climbing the second mountain involves reducing the frequency of vaping and reducing the level of nicotine to zero. The second mountain might take longer to climb or might go quickly. That's up to you, but in order to become a non-smoker you have to give up the cigarettes.\n" +
                "\n" +
                "Let's get going. Transitioning from smoking, not vaping to vaping and smoking equally to vaping, not smoking should take less than a month. How long do you need to say goodbye to cigarettes?\n");
        addArticle("Day 4 - Applying the 5 Stages of Grief Model to Quitting Smoking","If giving up smoking is like the death of a close friend, then maybe it is worthwhile to compare stopping smoking to the 5 Stages of Grief model.\n" +
                "\n" +
                "The 5 Stages of Grief model was developed by Swiss-American psychologist Elisabeth Kubler-Ross and published in her book On Death and Dying in 1969. The model was originally intended as a model of emotions for people dying of a terminal illness. It became a general model for grief and bereavement in popular culture. The model suggests that most people go through five emotions, usually in this order, as a bereavement process. These five emotions and their usual order are: Denial, Anger, Bargaining, Depression and Acceptance. \n" +
                "\n" +
                "Are smokers in denial about smoking or quitting smoking? Can you quit smoking? Yes, anytime I want to! Sound familiar? Do you want to quit smoking? No, I like smoking! But everyone knows that they should quit smoking! Do you enjoy every cigarette? Yes, I enjoy most of them. Do you know that 1 in 20 smokers dies of cancer? Yes, but it won't happen to me! Really?!?! It seems that smokers live largely in denial.\n" +
                "\n" +
                "What about anger? I'm angry that I'm still smoking. I'm angry that I can't quit smoking. I'm angry that I started smoking again. I'm angry that cigarettes are so expensive! Bargaining? Yup! I'll quit smoking next year. Depression? I can't quit smoking. It's too difficult! And Acceptance? I accept that I am a smoker. I accept that I can't quit smoking.\n" +
                "\n" +
                "But this is not how the 5 Stages of Grief model is supposed to work. These examples posited above are examples of feedback loops, negative feedback loops, and this is a main reason why smoking is so hard for people to quit.\n" +
                "\n" +
                "In my opinion, there are 3 main reasons why smoking is difficult to stop:\n" +
                "1. It is a deeply ingrained habit\n" +
                "2. Most smokers are dependent on nicotine and have developed a tolerance for this dangerous chemical\n" +
                "3. Feedback loops, especially negative feedback loops, reinforce and self-perpetuate the smoking habit.\n" +
                "\n" +
                "These three main elements of the smoking habit can all be overcome using the Two Mountains Stop-Smoking Program\n" +
                "\n" +
                "Dr. Stephen Feldman wrote a response to the 5 Stages of Grief model that was published in Psychology Today magazine.  He suggests that there are three lessons that can be learned from it. 1. A little denial is natural. Applied to smoking, smokers use denial as a psychological defense mechanism. Denial helps them cope with internal, mental conflicts caused by continuing this self-harming habit. 2. Grief can shake your faith. Faith in yourself, faith in the future and a belief that the world will never be the same again are examples of how faith gets shaken by the prospects of either remaining a smoker, a realization that you cannot change, or of becoming a non-smoker, a big unknown in a different, unimaginable future. Smoking is a comfort to smokers, not smoking is an unknown future, therefore a discomfort to smokers. Don't worry! Not smoking will be a huge comfort to you in the future! 3. Grief usually leads to acceptance. And this is the acceptance that every smoker needs to get to. Accept that your life will be much better as a non-smoker. I quit smoking 8 years ago. I am much happier as a non-smoker than I ever was as a smoker.\n" +
                "\n" +
                "Acceptance, this is the key! Accept that you have to change and get on with it. \n" +
                "Accept that you can successfully quit smoking! Accept that you will have a better future without cigarettes than with cigarettes. Accept that you will know and understand this fully only after being smoke-free for one full year.\n" +
                "\n" +
                "This is your goal!\n");
        addArticle("Day 5 - Are you there yet?","Day 5, 10 vape uses/day and 10 cigarettes/day, are you there yet? Is it easy to increase your vape uses? Are you getting used to this product? Do you like it?\n" +
                "\n" +
                "Is it easy for you to drop 2 cigarettes/day? Do you realize that you have cut your cigarette consumption in half already? Are you ready and motivated to cut your smoking to zero? \n" +
                "\n" +
                "In order to progress to 0 cigarettes/day, it will be beneficial for you to set a quit date. That date should not be too far in future. Two days ago I already asked you, \"How long do you need to say goodbye to cigarettes?\"\n" +
                "\n" +
                "Your Quit Day is important. It is your first smoke-free day. I could become you stop-smoking anniversary day, if you don't succumb to a relapse of smoking. It should be a quiet and calm day, not a social day, like a Friday or Saturday, if you are a social smoker or usually drink alcohol and smoke at the same time. Alcohol can easily trip you up, due to impaired thinking. That's what alcohol often does! You need to plan and prepare for that one!\n" +
                "\n" +
                "So? When is your quit date? 5 days to get to 0 cigarettes/day, maybe + a few more days to quit on a Sunday, Monday or Tuesday? These are the best quit days. Time to prepare, time to get ready. Time to say goodbye to cigarettes. Good riddance!\n" +
                "\n" +
                "If you can't set a Quit Day or your preferred Quit Day is more than 10 days from now, then you are not yet ready to quit. You are not learning enough yet or you are not confident enough yet or you are not supported enough yet to try to quit? Well you get 5 to 10 more days to get there. Really, how long do you need to say goodbye to cigarettes?\n");
        addArticle("Day 6 - Creating Support","This stop-smoking program cannot give you enough support to quit smoking. You need to build your own support circles. Tell your family that you are going to quit smoking in X days, on ________ (fill in the blank). This is your family support circle.\n" +
                "\n" +
                "Tell your friends the same thing.\"I'm going to quit smoking in X days, on ________.\" This will be your friends support circle. Tell your colleagues. This will be your work support circle. Find other people who are quitting smoking, create a stop-smoking support circle. The more people you tell the more likely you are to successfully quit smoking. If you keep this private, then you are more likely to renege, to relapse back to smoking. If you proclaim it publicly, then you are more likely to succeed, because you don't want to lose face and you will gain immeasurable support from nearly everyone you tell. Nearly everyone will want you to succeed, even the smokers, because they will try to quit next, after you show them that you succeeded.\n" +
                "\n" +
                "Get peoples' contact details. Ask them if you can call them when you are feeling a cigarette desire. Ask them if you can count on them to support you before you falter and after, if you falter. Tell them you will try again immediately if you have a smoking relapse. Don't plan on a smoking relapse, just a plan, a promise to yourself, to try again, if you succumb to a cigarette desire. It's not a nicotine craving. You are getting your regular nicotine consumption from the X-pedition1 vape. It is not uncommon to a smoking relapse. It's not the end of the world. The only thing that happens is that your smoke-free calendar gets reset to 0 and you have to start counting smoke-free days again. A hard count of 90 smoke-free days is necessary, because smoking is a hard habit to over-come. 90 true smoke-free days, that is the summit of the first mountain. \n" +
                "\n" +
                "Print two calendars, one for work and one for your home. You can find free, printable calendars online. Try https://free-printablecalendar.com/monthly-2019 or https://www.calendardate.com/2019_printable_calendars.htm. Put them in a prominent position, one at work and one at home. Mark your Quit Day on both of these calendars. Get ready for a hard count, putting Xs on the calendars, for each smoke-free day. Take pride and look forward to marking those two calendars with an X for each smoke-free day. If you relapse to smoking, even just one cigarette, those calendars need to come down and be replaced with new calendars. The hard count needs to be restarted. 90 days is 90 days. A true 90 days is your goal, your first summit.\n");
        addArticle("Day 7 - If you really want to quit smoking use the power of positive thinking","If you really want to quit smoking then you really have to want to quit smoking.\n" +
                "\n" +
                "Everyone knows that they should quit smoking but that is not enough. You want to quit smoking but that is not enough either. There are many factors working against you, that is why the smoking cessation rate is so low - only 8% if you are unassisted! \n" +
                "\n" +
                "You have to break the habit of smoking. It takes a long time to form a new good habit. It takes a long time to break a bad habit, like smoking. Dr. Travis Bradberry posits that habits are formed or broken over 66 days on average.  That's why it is difficult to keep New Year's resolutions, like going to the gym regularly. After a month or two, done. We didn't know that it would take this long to break or establish habits. That is why you have to set a Quit Day and start marking Xs on calendars in a hard count of 90 days. This is a mountain that you have to climb. Smoking is not an easy habit to break. If it was then you would not be needing my help.\n" +
                "\n" +
                "Smoking is a chemical dependence that needs to be dealt with also. But we put that on the back burner and hope to deal with that problem later. Now you have a substitute product that was specifically designed to help you to quit smoking, your X-pedition1 vape unit. It mimics smoking in many ways and roughly counts how much nicotine you are consuming in units that you are comfortable with. 1 vape use = 1 cigarette If you want to quit smoking then vape, don't smoke.\n" +
                "\n" +
                "You also have many feedback loops related to smoking. These self-perpetuate the smoking habit and make it so hard to quit. One tool that you can use is the Power of Positive Thinking. Don't under-estimate this. The key to the Power of Positive Thinking is repetition.\n" +
                "\n" +
                "\"I should quit. I want to quit. I will quit\", is a good mantra to repeat to yourself. \n" +
                "\"I will quit, I will quit, I will quit!\", might also be a good one. Mantras are a cornerstone of meditation and are comparable to prayers. Mantras utilize the Power of Positive Thinking, compounded by the Power of Repetition, to effect a positive spiritual or psychological outcome. Repeat mantras frequently in your mind. This is called self-talk in psychology, where you hear your own thoughts in your mind, conscious thoughts. Repeat mantras frequently throughout the day. Repeat mantras before sleeping and upon waking. The more you repeat these mantras, the more powerful they become. The Power of Repetition.\n" +
                "\n" +
                "\"Vape, don't smoke. Vape, don't smoke. Vape, don't smoke.\"\n");
        addArticle("Day 8 – Triggers","Did you read the article that was referenced in yesterday's Daily Reading, in the foot note? The title is How to Break a Bad Habit - For Good. This article is not specifically about quitting smoking, but it is loaded with some great information! You should read it now, before reading this Daily Reading article.\n" +
                "\n" +
                "https://www.huffpost.com/entry/how-to-break-a-bad-habit_n_13232244\n" +
                "\n" +
                "Let's discuss triggers now. A trigger is prompt that initiates a behavior, like smoking. The behavior has some reward, for smoking it is nicotine hitting your brain. That reward causes you, consciously or unconsciously, to repeat the behavior. Dr. Travis Bradberry calls this the habit loop.\n" +
                "\n" +
                "Habit loops, feedback loops, in psychology this all goes back to Pavlov's dogs. Ivan Pavlov experimented with dogs to learn about the physiology of the digestive system. He was awarded the Nobel Prize in Medicine for this research in 1904. As well as learning more about the digestive system, Pavlov serendipitously discovered key elements that have become cornerstones of behaviorist psychology. Pavlov discovered classic conditioning. A stimulus causes a response. In regards to smoking, a trigger is a stimulus, and the response is smoking.\n" +
                "\n" +
                "Smoking, however, is a conditioned response, not an unconditioned response. When humans or dogs see or smell appealing food, it is instinctual (unlearned) to begin salivating. When you see a pack of cigarettes or you see someone smoking, then you have the conditioned response (learned) of wanting a smoke. Seeing a pack of cigarettes or seeing someone smoking are conditioned stimuli, triggers, for you.\n" +
                "\n" +
                "The good news is that since smoking is a learned behavior, you can learn to dislike and detest smoking. You can learn your triggers and learn to disarm them. You can learn to quit smoking. The bad news is that you have many triggers and your brain wants that nicotine reward. You also have the conditioned response of fear or anxiety when either your pack of cigarettes is nearly empty or your lighter is low on fluid. You have conditioned yourself to automatically (without thinking) want to smoke!\n" +
                "\n" +
                "What are your smoking triggers? Any sight or smell of smoking - if you see a cigarette or a pack of cigarettes or smell cigarette smoke. The time of day - you smoke on a schedule. Do you smoke first thing in the morning? After lunch and dinner? In social settings? Do you associate smoking with your favorite beverages? Do you smoke while drinking coffee? Do you smoke while drinking beer or alcohol? Most of your emotions are triggers - you smoke when you are happy, when you are unhappy, when bored, when stressed, when angry. \n" +
                "\n" +
                "Awareness of these smoking triggers is the first step towards disarming them. The second step is introspection, thoughtfulness, mindfulness. Are these all of your smoking triggers? Why do they effect you so? The third step is consciously avoiding or opposing these smoking triggers.\n" +
                "\n" +
                "Since opposing and avoiding triggers is difficult, you have one more great way to help yourself to quit smoking. The X-pedition1 vape unit is a substitution product. You get the nicotine reward hitting your brain when you use the X-pedition1 in a responsible way. 1 use = 1 cigarette\n" +
                "\n" +
                "First, tackle the smoking habit. Transition to the X-pedition1 vape unit and extinguish the smoking habit. The first mountain to climb is stopping smoking. The summit is 90 smoke-free days.\n" +
                "\n" +
                "\n" +
                "\n" +
                "P.S. If you really want to quit smoking, for good, then you need to read a lot more and be proactive about taking control, accountability and actions to end this dangerous and unhealthy habit, smoking. Did you set-up your stop-smoking support groups. These social support networks and the support they will provide to you are going to be important to you in the future. They could prevent you from having a smoking relapse, and prevent you from having to start your 90 smoke-free day count over.\n");
        addArticle("Day 9 - Deep Psychology: Cognitive Dissonance and Gestalt Psychology","Why do I ask you these two Daily Questions? \"How many cigarettes did you smoke yesterday?\" and \"How determined are you to quit smoking?\" Sure, your answers provide a great graphing opportunity. The APP graph can add some motivation for most people to continue climbing the first mountain: 90 smoke-free days.\n" +
                "But more than this, I am trying to create a 'cognitive dissonance' within some of you. If your cigarette consumption is decreasing and your determination to quit smoking is stable or getting higher, then you are on the right track and there is no cognitive dissonance, thinking disharmony. If your smoking consumption is not decreasing and you honestly declare that your determination to stop smoking is decreasing, then there is also no cognitive dissonance. You are accepting that you are not ready or prepared to quit smoking at this time. However, keep reading, please. I still want to persuade you to quit smoking. But if your smoking consumption is not decreasing and you are still declaring that your motivation to quit smoking is still high, now you are entering a state of cognitive dissonance. Your actions, continued smoking, and your thoughts, high determination to stop smoking, are not in agreement, thinking disharmony.\n" +
                "A state of cognitive dissonance is discomforting. You should either change your actions or your thinking so that they are in agreement. Either decrease and stop smoking and honestly declare your high determination to go 90 smoke-free days or accept that you are not prepared to quit smoking at this time. However, keep reading, please. I still want to persuade you to quit smoking.\n" +
                "Cognitive dissonance is a psychological stress discovered and written about by Leon Festinger in 1957. Festinger was heavily influenced by his mentor, Kurt Lewin, a famous Gestalt psychologist. Gestalt is the German word for 'shape or form'. A central principle of Gestalt psychology is that everyone's minds form perceptions, called gestalts. These perceptions are cohesive, comprehensive, and whole views of how we see the world. Once these gestalts are formed, they are difficult to change.\n" +
                "Festinger infiltrated, and observed, a 'doomsday cult'. He predicted that when the appointed day of doom passed, that the cult members would abandon the cult as their psychological discomfort (cognitive dissonance) was unsustainable; between their actions, participation in the cult, and their thoughts, the day of doom had passed without incident. What Festinger discovered was the exact opposite! The cult members had strong convictions because of their gestalts (their way of seeing the world), so after the day of doom passed they became more fervent and found illogical ways justifying their gestalts (their way of thinking). They convinced themselves that their dedication to the cult had postponed the day of doom!\n" +
                "So, what does this have to do with you? Smokers have strong convictions because of how they view smoking (gestalts). Which illogical gestalts do you harbor in your mind regarding smoking? \n" +
                "\uF06C\tSmoking is OK (global)\n" +
                "\uF06C\tSmoking is OK for me (specific)\n" +
                "\uF06C\tSmoking is not OK, but it is OK for me (global but with specific exemption!)\n" +
                "\uF06C\tSmoking kills millions of people and makes a lot of people sick, but it won't happen to me (accepting of facts but with specific exemption!)\n" +
                "Smokers have conditioned themselves to be immune to evidence and rational arguments. With regards to smokers, \"Tell them that you disagree with them and they will turn away. Show them facts or figures and they will question your sources. Appeal to logic and they will fail to see your point.\" This was a famous quote by Leon Festinger, and he wasn't even talking about smokers. He was talking about a man with strong convictions. But smokers are people of strong convictions and therefore unlikely to change their opinions even in the face of rational, logical, emotional contradictions (cognitive dissonance).\n" +
                "Why are gestalts so hard to change? Festinger described that an Effort Justification Effect significantly contribute to preventing people from changing their strong convictions because of their gestalts. With regards to Effort Justification, the effect will be compounded if a great deal has been invested in the original belief. The longer that you have been smoking; the more you have invested in time, effort and money; the harder it will be to quit smoking because it will be more difficult to change your gestalt of smoking.\n" +
                "Festinger also described a Proximity Effect, that we have stronger relationships with people and things that are physically closer to us and weaker relationships with people and things that are physically farther away. With regards to smoking, where are your cigarettes and lighter? Always close to you. You feel uncomfortable when you are separated from them or prevented from smoking, like on a long flight or in a restaurant. Forcing smokers to go outside to smoke, at work or in a restaurant, are societal attempts to weaken the Proximity Effect. You should smoke less because you have to move farther away in order to smoke. What is a smoker's usual response? Indignation and anger! It is my right to smoke! It is my right to poison myself. It is my right to poison everyone around me. \n" +
                "Which brings me back to the 5 Stages of Grief. Which stage are you in? Denial, anger, bargaining, depression or acceptance? Only when you get to acceptance can you change your false gestalts about smoking and have the determination and conviction to go the required 90 smoke-free days. To change from a smoker to a non-smoker.\n");
        addArticle("Introducing the Two Mountains - You have to climb these (but don't worry)","Two Mountains is an analogy. It is a story, a personal story. It is a narrative, written by each person trying to quit smoking. Some people will have an easier time, they will climb two smaller mountains. Some people will have a very difficult time. They will choose the highest mountains; they will think that quitting smoking is the biggest challenge that they could possibly face or imagine.\n" +
                "\n" +
                "Quitting smoking can be unimaginably difficult or just mildly uncomfortable. It depends on each person individually and their mindset. If you think that quitting will be difficult, like climbing Mount Everest or the Matterhorn, then it will be exactly that difficult. If you think that it will be like climbing a smaller mountain or hill, like Mount Snowdon in Wales, then it won't be any more difficult than that. The secret to quitting smoking is to plan and prepare. Set your goals to climb two smaller mountains and quitting smoking will be no more difficult that these smaller expeditions.\n" +
                "\n" +
                "The Two Mountains Stop-Smoking Program teaches you to plan and prepare your stop-smoking expedition and then your quit-nicotine expedition. Using psychology and technology, we are breaking your smoking habit into two easier-to-manage challenges. First, we will teach you to break the smoking habit. Second, we will teach you to beat the nicotine dependency. You can learn how to plan and prepare to summit these two mountains successfully.\n" +
                "\n" +
                "Are you ready to plan and prepare your stop-smoking expedition?\n" +
                "Two Mountains will help you.\n");
        /*addArticle("How to Keep Your Motivation High?","Introduction: Maintaining high levels of motivation is essential for achieving success in any endeavor. However, staying motivated can be challenging, especially when faced with obstacles and setbacks. In this article, we will explore effective strategies to keep your motivation high and stay focused on your goals.\n\n1.Set Clear Goals: One of the key factors in staying motivated is having clear and specific goals. Define what you want to achieve and break it down into smaller, manageable tasks. Setting achievable milestones can help you track your progress and stay motivated along the way.\n\n2.Find Your Why: Understanding the reasons behind your goals can provide a powerful source of motivation. Reflect on why your goals are important to you and how they align with your values and aspirations. Connecting with your deeper purpose can fuel your motivation during challenging times.\n\n3.Create a Positive Environment: Surround yourself with positivity and inspiration. Seek out supportive and encouraging individuals who believe in your goals. Create a workspace that is conducive to productivity and motivation, filled with motivational quotes, images, or objects that inspire you.\n\n4.Celebrate Small Wins: Acknowledge and celebrate your achievements, no matter how small. Recognizing your progress and accomplishments can boost your confidence and motivation to keep moving forward. Take time to appreciate the effort you have put in and the steps you have taken towards your goals.\n\n5.Stay Flexible and Adapt: Be open to adjusting your plans and strategies as needed. Life is full of unexpected challenges and changes, and being adaptable can help you navigate obstacles without losing motivation. Embrace setbacks as learning opportunities and use them to grow stronger.\n\n6.Practice Self-Care: Taking care of your physical and mental well-being is crucial for maintaining motivation. Make time for activities that recharge you, such as exercise, meditation, hobbies, or spending time with loved ones. Prioritize self-care to ensure you have the energy and resilience to stay motivated.\n\n7.Stay Inspired: Seek inspiration from successful individuals, motivational books, podcasts, or videos. Learning from others' experiences and achievements can reignite your motivation and provide fresh perspectives on your goals. Stay curious, continue learning, and stay inspired on your journey.\n\nConclusion: Keeping your motivation high requires effort, commitment, and a positive mindset. By setting clear goals, finding your why, creating a supportive environment, celebrating wins, staying flexible, practicing self-care, and seeking inspiration, you can maintain a high level of motivation and stay on track towards achieving your aspirations. Remember that motivation is a journey, and it's okay to have ups and downs along the way. Stay focused, stay resilient, and keep your motivation burning bright.");
        addArticle("The Benefits of Quitting Smoking.","Smoking is a harmful habit that can have serious consequences on your health. However, making the decision to quit smoking can have a profound impact on your well-being and overall quality of life. Here are some of the benefits of quitting smoking:\n\n1.Improved Lung Health: Smoking damages the lungs and can lead to respiratory problems such as chronic bronchitis and emphysema. By quitting smoking, you can improve your lung function and reduce the risk of developing these conditions.\n\n2.Reduced Risk of Cancer: Smoking is a leading cause of various types of cancer, including lung, throat, and mouth cancer. Quitting smoking can significantly lower your risk of developing these life-threatening diseases.\n\n3.Better Cardiovascular Health: Smoking increases the risk of heart disease and stroke by damaging the blood vessels and increasing blood pressure. When you quit smoking, you reduce the strain on your heart and improve your cardiovascular health.\n\n4.Improved Sense of Taste and Smell: Smoking can dull your sense of taste and smell over time. By quitting smoking, you can regain these senses and enjoy the flavors and aromas of food and beverages.\n\n5.Enhanced Energy Levels: Smoking can cause fatigue and reduce energy levels due to the impact on oxygen levels in the body. Quitting smoking can lead to increased energy and vitality.\n\n6.Better Skin Health: Smoking accelerates skin aging and can lead to wrinkles and dull complexion. When you quit smoking, you can improve the health and appearance of your skin.\n\n7.Financial Savings: Smoking is an expensive habit that can drain your finances. By quitting smoking, you can save money that would have been spent on cigarettes and related health care costs.\n\nQuitting smoking is a challenging journey, but the benefits of a smoke-free life are well worth the effort. If you are considering quitting smoking, remember that there is support available to help you along the way. Your health and well-being will thank you for making the decision to quit smoking.");
        addArticle("Tips for Quitting Smoking Successfully.","Quitting smoking is a challenging but rewarding journey that can have a positive impact on your health and well-being. If you are ready to take the step towards a smoke-free life, here are some helpful tips to guide you through the process:\n\n1.Set a Quit Date: Choose a specific date to quit smoking and mark it on your calendar. Having a clear goal in mind can help you stay motivated and committed to your decision.\n\n2.Identify Triggers: Pay attention to situations, emotions, or activities that trigger your smoking habit. By identifying these triggers, you can develop strategies to avoid or cope with them without reaching for a cigarette.\n\n3.Seek Support: Inform your friends, family, and colleagues about your decision to quit smoking. Their support and encouragement can make a significant difference in your journey towards a smoke-free life.\n\n4.Consider Nicotine Replacement Therapy: Nicotine replacement therapy, such as nicotine patches or gum, can help reduce withdrawal symptoms and cravings associated with quitting smoking. Consult with a healthcare provider to determine the best option for you.\n\n5.Stay Active: Engage in physical activities or hobbies that can distract you from the urge to smoke. Exercise can also help reduce stress and improve your overall well-being.\n\n6.Practice Relaxation Techniques: Explore relaxation techniques such as deep breathing, meditation, or yoga to manage stress and cravings during the quitting process.\n\n7.Reward Yourself: Celebrate small milestones and achievements along the way. Treat yourself to something special or indulge in activities that bring you joy as a reward for your progress.\n\n8.Stay Positive: Quitting smoking is a journey with ups and downs. Stay positive and remind yourself of the reasons why you decided to quit in the first place. Focus on the benefits of a smoke-free life.\n\n9.Prepare for Challenges: Be prepared for challenges and setbacks during the quitting process. Remember that slip-ups are a natural part of the journey, and it's important to stay resilient and committed to your goal.\n\n10.Stay Persistent: Quitting smoking may not be easy, but it is possible with determination and perseverance. Stay persistent and believe in your ability to lead a healthier, smoke-free life.\n\nRemember, you are not alone in your journey to quit smoking. Reach out for support, stay committed to your goal, and celebrate each step towards a healthier, smoke-free you.");
        addArticle("Breaking Free: The Journey to Quit Smoking.","Quitting smoking is a transformative journey that requires determination, resilience, and a commitment to better health. The decision to break free from the grip of cigarettes marks the beginning of a profound personal transformation. It is a journey filled with challenges, triumphs, and self-discovery.\n\nThe first step in the journey to quit smoking is acknowledging the harmful effects of smoking on your health and well-being. Understanding the risks associated with smoking can serve as a powerful motivator to embark on the path to a smoke-free life. It is a decision that stems from self-care and a desire for a healthier future.\n\nAs you navigate the journey to quit smoking, you may encounter various obstacles along the way. Nicotine cravings, withdrawal symptoms, and triggers that tempt you to reach for a cigarette can test your resolve. However, with each challenge overcome, you grow stronger and more determined to stay on course.\n\nSeeking support from friends, family, or support groups can provide encouragement and accountability during the quitting process. Sharing your struggles and successes with others who understand the journey can offer valuable insights and motivation to stay committed to your goal.\n\nThe journey to quit smoking is not just about breaking a habit; it is about reclaiming control over your life and health. It is a journey of self-discovery, self-improvement, and empowerment. With each smoke-free day, you take a step closer to a brighter, healthier future.\n\nCelebrate each milestone, no matter how small, as a testament to your strength and resilience. Recognize the progress you have made and the positive changes that quitting smoking has brought into your life. Embrace the journey as an opportunity for growth and transformation.\n\nBreaking free from the grip of smoking is a courageous and empowering choice. It is a journey of self-liberation, renewal, and empowerment. As you navigate the ups and downs of quitting smoking, remember that you are embarking on a journey towards a healthier, smoke-free life. Stay strong, stay committed, and embrace the journey to break free from smoking.");
        String title = "Healthy Eating: Nutrition Tips for New Non-Smokers";
        String content = "Introduction: As you quit smoking, focusing on a healthy diet can aid your body’s recovery and help manage withdrawal symptoms. This article provides nutrition tips to support your journey to becoming smoke-free.\n" +
                "1. Hydration: Drink plenty of water to flush out toxins and keep your body hydrated.\n" +
                "2. Antioxidant-Rich Foods: Consume fruits and vegetables high in antioxidants, such as berries, spinach, and nuts, to help repair cell damage caused by smoking.\n" +
                "3. Fiber-Rich Foods: Increase your intake of fiber to support digestive health, including whole grains, legumes, and vegetables.\n" +
                "4. Healthy Snacks: Choose healthy snacks like nuts, seeds, and fruits to help manage cravings without gaining weight.\n" +
                "5. Avoid Junk Food: Steer clear of sugary and processed foods which can lead to weight gain and leave you feeling sluggish.\n" +
                "6. Vitamin C: Smoking depletes vitamin C, so include citrus fruits, bell peppers, and tomatoes in your diet to replenish your levels.\n" +
                "7. Protein: Ensure you have enough protein to support muscle repair and overall health, from sources like lean meats, eggs, beans, and dairy products.\n" +
                "Conclusion: Nutrition plays a crucial role in the recovery process after quitting smoking. By focusing on a balanced and healthy diet, you can support your body’s healing and manage the challenges of becoming smoke-free more effectively.";
        addArticle(title, content);*/
        insertFm1();
        insertFm2();
        insertFm3();
        insertFm4();
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
        administratorBean.password = "A12345678";
        DBCreator.getAdministratorDao().registerAdministrator(administratorBean);
    }

    public static void deleteAdministrator(int id){
        DBCreator.getAdministratorDao().deleteAdministratorById(id);
    }

    private static void insertFm2() {

        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 2;
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
        fmBean.type = 4;
        fmBean.upId = 1;
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

    private static void insertFm4() {
        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "曾晓";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1008456236_20220306203757_0.jpeg?x-oss-process=image/quality,Q_80/format,jpg";
        fmBean.fmTitle = "探索内心宁静的旅程 | 宁静之声";
        fmBean.fmSecTitle = "在内心的宁静深处，你与自己的对话开始了。你穿越了一切情感，跨越了所有自我的批判和他人的对待，最终到达了最真实的自己。你聆听心中最微弱的声音，在黑暗中适应，眼睛点亮夜晚，心中燃烧烈焰。即使只是在等待，你也紧握双手，知道黎明一定会到来。在这片宁静中，你与自己真诚相对，爱的力量在这里悄然发生。";
        DBCreator.getFMDao().insert(fmBean);
    }

    /*private static void insertFm5() {
        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "Arabella Rosalind";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://ossimg.xinli001.com/20220307/4ea464a99e871e4fc09ec76f22f1c1ad.jpg?x-oss-process=image/quality,Q_80/format,jpg";
        fmBean.fmTitle = "Love yourself, happening in the depths of tranquility | Xiaosheng Listening";
        fmBean.fmSecTitle = "You can be very close to yourself, go through all emotions, those judgments of yourself, the way others treat you, and then reach yourself. You can wait for the night to fall, adapt to the arrival of darkness, and in the depths of tranquility, listen to the slightest voice in your heart. Your eyes will light up the night, your heart will burn flames, and in the depths of tranquility, you will respond to yourself. Even if it's just waiting, hold your hand tightly and know that dawn will surely come. In the depths of tranquility, you are sincere with yourself. In the depths of tranquility, love is happening.";
        DBCreator.getFMDao().insert(fmBean);
    }

    private static void insertFm6() {
        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "Arabella Rosalind";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1006942126_20220306225359_1.jpeg?x-oss-process=image/quality,Q_80/format,jpg";
        fmBean.fmTitle = "Love yourself, happening in the depths of tranquility | Xiaosheng Listening";
        fmBean.fmSecTitle = "You can be very close to yourself, go through all emotions, those judgments of yourself, the way others treat you, and then reach yourself. You can wait for the night to fall, adapt to the arrival of darkness, and in the depths of tranquility, listen to the slightest voice in your heart. Your eyes will light up the night, your heart will burn flames, and in the depths of tranquility, you will respond to yourself. Even if it's just waiting, hold your hand tightly and know that dawn will surely come. In the depths of tranquility, you are sincere with yourself. In the depths of tranquility, love is happening.";
        DBCreator.getFMDao().insert(fmBean);
    }

    private static void insertFm7() {
        FMBean fmBean = new FMBean();
        fmBean.up = "程楠";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "Arabella Rosalind";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1006942126_20220306225359_0.jpeg?x-oss-process=image/quality,Q_80/format,jpg";
        fmBean.fmTitle = "Love yourself, happening in the depths of tranquility | Xiaosheng Listening";
        fmBean.fmSecTitle = "You can be very close to yourself, go through all emotions, those judgments of yourself, the way others treat you, and then reach yourself. You can wait for the night to fall, adapt to the arrival of darkness, and in the depths of tranquility, listen to the slightest voice in your heart. Your eyes will light up the night, your heart will burn flames, and in the depths of tranquility, you will respond to yourself. Even if it's just waiting, hold your hand tightly and know that dawn will surely come. In the depths of tranquility, you are sincere with yourself. In the depths of tranquility, love is happening.";
        DBCreator.getFMDao().insert(fmBean);
    }*/

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
