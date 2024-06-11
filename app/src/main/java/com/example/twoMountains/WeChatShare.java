package com.example.twoMountains;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

public class WeChatShare {
    private static final String APP_ID = "wx68c3d9bac60154ed";
    private IWXAPI api;

    public WeChatShare(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    public void shareText(String text) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 分享到微信聊天，若需分享至朋友圈，请使用WXSceneTimeline

        api.sendReq(req);
    }

    public void shareImage(Bitmap image, String description) {
        WXImageObject imageObject = new WXImageObject(image);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        msg.description = description;

        Bitmap thumbBitmap = Bitmap.createScaledBitmap(image, 150, 150, true);
        msg.setThumbImage(compressBitmap(thumbBitmap, 32));

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 分享到微信聊天，若需分享至朋友圈，请使用WXSceneTimeline

        api.sendReq(req);
    }

    public void shareWebPage(String url, String title, String description, Bitmap thumb) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        // 压缩 Bitmap
        Bitmap thumbBitmap = compressBitmap(thumb, 32);
        msg.setThumbImage(thumbBitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        api.sendReq(req);
    }

    public Bitmap compressBitmap(Bitmap bitmap, int maxSizeKB) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
        while (bos.toByteArray().length / 1024 > maxSizeKB && options > 10) {
            bos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
        }
        byte[] compressedBytes = bos.toByteArray();
        return BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.length);
    }
}
