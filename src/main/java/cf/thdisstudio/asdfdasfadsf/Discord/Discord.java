package cf.thdisstudio.asdfdasfadsf.Discord;

import cf.thdisstudio.asdfdasfadsf.Asdfdasfadsf;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;

import javax.security.auth.login.LoginException;

public class Discord extends Thread {

    public static JDA jda =null;

    @Override
    public void run() {
        JDABuilder builder = JDABuilder.createDefault("Token");
        builder
                // Disable compression (not recommended)
                .setCompression(Compression.NONE)
                // Set activity (like "playing Something")
                .setActivity(Activity.watching("마인크래프트 및 채팅"))

                .setBulkDeleteSplittingEnabled(false)

                .enableIntents(GatewayIntent.GUILD_MEMBERS)

                .addEventListeners(new bot());
        try {
            jda = builder.build();
            Asdfdasfadsf.j = jda;
        } catch (LoginException e) {
            e.printStackTrace();
        }
        Asdfdasfadsf.l.info("마인크래프트 유저 그룹 인증을 성공적으로 로딩을 완료하였습니다!");
    }

//    public static void main(String[] args) {
//        JDABuilder builder = JDABuilder.createDefault("OTA5NzcwOTcwNTM1OTcyODc1.YZJIPg.48pVVn7_lR7T1vhIO9ZqJnw9tIg");
//        builder
//                // Disable compression (not recommended)
//                .setCompression(Compression.NONE)
//                // Set activity (like "playing Something")
//                .setActivity(Activity.watching("마인크래프트 및 채팅"))
//
//                .setBulkDeleteSplittingEnabled(false)
//
//                .enableIntents(GatewayIntent.GUILD_MEMBERS)
//
//                .addEventListeners(new bot());
//        try {
//            jda = builder.build();
//            Asdfdasfadsf.j = jda;
//        } catch (LoginException e) {
//            e.printStackTrace();
//        }
//    }
}
