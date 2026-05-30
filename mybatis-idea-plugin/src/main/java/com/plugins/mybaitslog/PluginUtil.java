package com.plugins.mybaitslog;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.extensions.PluginId;
import com.plugins.mybaitslog.gui.FilterSetting;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
import java.io.File;
import java.net.URL;

public class PluginUtil {

    /**
     * 获取核心Jar路径
     *
     * @return String
     */
    public static String getAgentCoreJarPath() {
        return getJarPathByStartWith();
    }

    /**
     * 根据jar包的前缀名称获路径
     *
     * @return String
     */
    private static String getJarPathByStartWith() {
        PluginId pluginId = PluginId.getId("com.linkkou.plugin.intellij.assistant.mybaitslog");
        final File filePlugin = PluginManagerCore.getPlugin(pluginId).getPluginPath().toFile();
        final File[] fileslibs = filePlugin.listFiles();
        for (File listFile : fileslibs) {
            if ("lib".equals(listFile.getName())) {
                final File[] fileslib = listFile.listFiles();
                for (File file : fileslib) {
                    //优化非写死
                    if (file.getName().contains("mybatis-agent")) {
                        return file.toPath().toString();
                    }
                }
            }
        }
        return null;
    }

    private static final String NOTIFICATION_GROUP_ID = "MyBatisLog Notification";

    private static NotificationGroup getNotificationGroup() {
        return NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP_ID);
    }

    public static void Notificat_AddConfiguration() {
        String content = "There is a new unknown actuator, Please exclude the options for configuration.<a href=\"configuration\">Open the configuration window</a>";
        Notification notification = getNotificationGroup()
                .createNotification("MyBatis Log EasyPlus", content, NotificationType.WARNING);
        notification.setListener(new NotificationListener.Adapter() {
            @Override
            protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent e) {
                FilterSetting dialog = new FilterSetting();
                dialog.pack();
                dialog.setSize(520, 420);
                dialog.setResizable(true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        Notifications.Bus.notify(notification);
    }

    public static void Notificat_Success() {
        String content = "MyBatis Log EasyPlus Run";
        Notification notification = getNotificationGroup()
                .createNotification("MyBatis Log EasyPlus", content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    public static void Notificat_Error(String error) {
        String content = error + ", MyBatis Log EasyPlus Unable to Run";
        Notification notification = getNotificationGroup()
                .createNotification("MyBatis Log EasyPlus", content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

}
