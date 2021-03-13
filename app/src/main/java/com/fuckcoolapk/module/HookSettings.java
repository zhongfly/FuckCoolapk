package com.fuckcoolapk.module;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bytedance.sdk.openadsdk.a.DKt;
import com.fuckcoolapk.AppConfigKt;
import com.fuckcoolapk.BuildConfig;
import com.fuckcoolapk.utils.AppUtilKt;
import com.fuckcoolapk.utils.CoolapkAuthUtilKt;
import com.fuckcoolapk.utils.CoolapkContext;
import com.fuckcoolapk.utils.GetUtil;
import com.fuckcoolapk.utils.LogUtil;
import com.fuckcoolapk.utils.OwnSP;
import com.fuckcoolapk.view.AdjustImageView;
import com.fuckcoolapk.view.AdjustImageViewForHook;
import com.fuckcoolapk.view.ClickableTextViewForHook;
import com.fuckcoolapk.view.EditTextForHook;
import com.fuckcoolapk.view.SwitchForHook;
import com.fuckcoolapk.view.TextViewForHook;

import java.util.List;
import java.util.function.Function;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HookSettings {
    private Boolean isOpen = false;

    public void init() {
        try {
            XposedHelpers.findAndHookMethod("com.coolapk.market.view.settings.VXSettingsFragment", CoolapkContext.classLoader, "initData", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Object fuckcoolapkHolderItem = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.coolapk.market.model.HolderItem", CoolapkContext.classLoader), "newBuilder");
                    fuckcoolapkHolderItem = XposedHelpers.callMethod(fuckcoolapkHolderItem, "entityType", "holder_item");
                    Object lineHolderItem = XposedHelpers.callMethod(fuckcoolapkHolderItem, "intValue", 14);
                    lineHolderItem = XposedHelpers.callMethod(lineHolderItem, "build");
                    fuckcoolapkHolderItem = OwnSP.INSTANCE.getOwnSP().getBoolean("agreeEULA", false) ? XposedHelpers.callMethod(fuckcoolapkHolderItem, "string", "Fuck Coolapk") : XposedHelpers.callMethod(fuckcoolapkHolderItem, "string", "Fuck Coolapk（未激活）");
                    fuckcoolapkHolderItem = XposedHelpers.callMethod(fuckcoolapkHolderItem, "intValue", 233);
                    fuckcoolapkHolderItem = XposedHelpers.callMethod(fuckcoolapkHolderItem, "build");
                    List list = (List) XposedHelpers.callMethod(param.thisObject, "getDataList");
                    list.add(fuckcoolapkHolderItem);
                    list.add(lineHolderItem);
                    //Log.v(AppConfig.TAG,fuckcoolapkHolderItem.toString());
                    super.beforeHookedMethod(param);
                }
            });
            XposedHelpers.findAndHookConstructor("com.coolapk.market.view.settings.VXSettingsFragment$onCreateViewHolder$1", CoolapkContext.classLoader, "com.coolapk.market.view.settings.VXSettingsFragment", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List list = (List) XposedHelpers.callMethod(param.args[0], "getDataList");
                    XposedHelpers.findAndHookMethod("com.coolapk.market.view.settings.VXSettingsFragment$onCreateViewHolder$1", CoolapkContext.classLoader, "onItemClick", "androidx.recyclerview.widget.RecyclerView$ViewHolder", View.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Object obj = list.get((Integer) XposedHelpers.callMethod(param.args[0], "getAdapterPosition"));
                            Integer intValue = (Integer) XposedHelpers.callMethod(obj, "getIntValue");
                            //Log.v(AppConfig.TAG, intValue.toString());
                            if (intValue != null & intValue == 233 & !isOpen) {
                                if (OwnSP.INSTANCE.getOwnSP().getBoolean("agreeEULA", false)) {
                                    showSettingsDialog();
                                } else {
                                    boolean useFastgit = false;
                                    new GetUtil().sendGet(useFastgit ? "https://hub.fastgit.org/ejiaogl/FuckCoolapk/raw/master/EULA.md" : "https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@master/EULA.md", result -> DKt.showEulaDialog(CoolapkContext.activity, result));
                                }
                                isOpen = true;
                            }
                            super.beforeHookedMethod(param);
                        }
                    });
                    super.afterHookedMethod(param);
                }
            });
        } catch (Throwable e) {
            LogUtil.e(e);
        }
    }

    private void showSettingsDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(CoolapkContext.activity);
        ScrollView scrollView = new ScrollView(CoolapkContext.activity);
        scrollView.setOverScrollMode(2);
        LinearLayout linearLayout = new LinearLayout(CoolapkContext.activity);
        scrollView.addView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(AppUtilKt.dp2px(CoolapkContext.context, 20), AppUtilKt.dp2px(CoolapkContext.context, 10), AppUtilKt.dp2px(CoolapkContext.context, 20), AppUtilKt.dp2px(CoolapkContext.context, 5));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "Fuck Coolapk", TextViewForHook.titleSize, null));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, BuildConfig.VERSION_NAME + " " + BuildConfig.VERSION_CODE + " " + BuildConfig.BUILD_TYPE + "\nTarget Version: " + AppConfigKt.MODULE_TARGET_VERSION, null, null));
        AdjustImageViewForHook imageView = new AdjustImageViewForHook(CoolapkContext.activity);
        linearLayout.addView(imageView);
        imageView.setUrl("https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@316/art/316-cover.png",adjustImageView -> null);
        imageView.setOnClickListener(v -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/issues/13"))));
        //Glide.with(imageView).load("https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@316/art/316-cover.png").into(imageView);
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "功能", TextViewForHook.title2Size, AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "去除启动广告", OwnSP.INSTANCE.getOwnSP(), "removeStartupAds", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "去除信息流广告（Alpha）", OwnSP.INSTANCE.getOwnSP(), "removeFeedAds", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "去除帖子下方广告（Alpha）", OwnSP.INSTANCE.getOwnSP(), "removeBannerAds", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "去除底部按钮（Alpha）", OwnSP.INSTANCE.getOwnSP(), "hideBottomBtn", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "去除动态审核水印", OwnSP.INSTANCE.getOwnSP(), "removeAuditWatermark", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "允许在应用列表内卸载酷安", OwnSP.INSTANCE.getOwnSP(), "allowUninstallCoolapk", false));
        //linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "开启频道自由编辑", OwnSP.INSTANCE.getOwnSP(), "enableChannelEdit", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "对动态开启 Markdown（Alpha）", OwnSP.INSTANCE.getOwnSP(), "enableMarkdown", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "对私信开启反和谐", OwnSP.INSTANCE.getOwnSP(), "antiMessageCensorship", false, "通过自动替换相似字来达到反和谐的效果，不能保证一定有效。\n请勿滥用，请勿用于除私信外的其他地方，否则后果自负。"));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "管理员模式", OwnSP.INSTANCE.getOwnSP(), "adminMode", false, "慎重开启，开启后很有可能导致你号没了！"));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "关闭链接追踪", OwnSP.INSTANCE.getOwnSP(), "disableURLTracking", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "关闭 Umeng", OwnSP.INSTANCE.getOwnSP(), "disableUmeng", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "关闭 Bugly", OwnSP.INSTANCE.getOwnSP(), "disableBugly", false));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "自定义水印", null, null, view -> showWaterMarkDialog()));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "其他", TextViewForHook.title2Size, AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "检查更新", OwnSP.INSTANCE.getOwnSP(), "checkUpdate", true));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "调试", TextViewForHook.title2Size, AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        //linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "临时输出统计内容", OwnSP.INSTANCE.getOwnSP(), "statisticToast", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "对 酷安 进行脱壳", OwnSP.INSTANCE.getOwnSP(), "shouldShelling", false, "不适用于较新的 Android 版本。\n重启应用后开始脱壳，文件存放在 /data/data/com.coolapk.market/fuck_coolapk_shell。"));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "输出调试 Toast", OwnSP.INSTANCE.getOwnSP(), "showLogToast", false));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "生成随机 Token", null, null, view -> {
            ClipboardManager clipboardManager = (ClipboardManager) CoolapkContext.context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("token", CoolapkAuthUtilKt.getAS()));
            LogUtil.INSTANCE.toast("已复制到剪贴板", true);
        }));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "信息", TextViewForHook.title2Size, AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        //linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "Xposed Module Repository", null, null, view -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://repo.xposed.info/module/com.fuckcoolapk")))));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "背景故事", null, null, view -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/wiki/Background-information")))));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "Telegram Channel", null, null, view -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/fuck_coolapk")))));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "GitHub", null, null, view -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk")))));
        linearLayout.addView(new ClickableTextViewForHook(CoolapkContext.activity, "FAQ", null, null, view -> CoolapkContext.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/wiki/FAQ")))));
        normalDialog.setView(scrollView);
        normalDialog.setPositiveButton("重启应用", (dialog, which) -> System.exit(0));
        AlertDialog alertDialog = normalDialog.show();
        alertDialog.setOnDismissListener(dialogInterface -> isOpen = false);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor(AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor(AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor(AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
    }

    private void showWaterMarkDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(CoolapkContext.activity);
        ScrollView scrollView = new ScrollView(CoolapkContext.activity);
        scrollView.setOverScrollMode(2);
        LinearLayout linearLayout = new LinearLayout(CoolapkContext.activity);
        scrollView.addView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(AppUtilKt.dp2px(CoolapkContext.context, 20), AppUtilKt.dp2px(CoolapkContext.context, 10), AppUtilKt.dp2px(CoolapkContext.context, 20), AppUtilKt.dp2px(CoolapkContext.context, 5));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "自定义水印", TextViewForHook.titleSize, null));
        linearLayout.addView(new TextViewForHook(CoolapkContext.activity, "切换后重启应用生效，请务必确保能生成预览再投入使用。", null, null));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "开启自定义水印", OwnSP.INSTANCE.getOwnSP(), "enableCustomWatermark", false));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "全覆盖水印", OwnSP.INSTANCE.getOwnSP(), "enableTileWatermark", true, "开启后水印位置参数将失效"));
        linearLayout.addView(new SwitchForHook(CoolapkContext.activity, "图片水印", OwnSP.INSTANCE.getOwnSP(), "enablePictureWatermark", false));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "水印文字（留空即为用户名）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkText", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "图片绝对位置", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkPicturePath", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "相对图片横坐标（0-1）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkPositionX", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "相对图片纵坐标（0-1）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkPositionY", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "选转角度（0-360）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkRotation", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "透明度（0-255）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkAlpha", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "文字颜色（#xxxxxx）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkTextColor", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "文字大小（dp）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkTextSize", ""));
        linearLayout.addView(new EditTextForHook(CoolapkContext.activity, "图片相对大小（0-1）", true, OwnSP.INSTANCE.getOwnSP(), "waterMarkPictureSize", ""));
        AdjustImageViewForHook imageView = new AdjustImageViewForHook(CoolapkContext.activity);
        refreshImageView(imageView);
        linearLayout.addView(imageView);
        normalDialog.setView(scrollView);
        normalDialog.setPositiveButton("刷新预览", null);
        AlertDialog alertDialog = normalDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor(AppUtilKt.getColorFixWithHashtag(AppUtilKt::getColorAccent)));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> refreshImageView(imageView));
    }

    private void refreshImageView(AdjustImageView imageView) {
        imageView.setUrl("https://cdn.jsdelivr.net/gh/lz233/src.lz233.github.io/image/background.jpg",adjustImageView -> {
            try {
                ModifyPictureWatermarkKt.doWaterMark(adjustImageView).setToImageView(adjustImageView);
            } catch (Throwable e) {
                LogUtil.INSTANCE.toast("生成水印时出现错误");
                LogUtil.e(e);
            }
            return null;
        });
    }
}
