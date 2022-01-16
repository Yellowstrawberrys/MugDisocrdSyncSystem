package cf.thdisstudio.asdfdasfadsf.Discord;

import cf.thdisstudio.asdfdasfadsf.Asdfdasfadsf;
import cf.thdisstudio.asdfdasfadsf.JsonReader;
import cf.thdisstudio.asdfdasfadsf.db.save;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import static cf.thdisstudio.asdfdasfadsf.Asdfdasfadsf.list;

public class bot extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(event.getName().equals("인증")){
            String op = event.getOptions().get(0).getAsString();
            EmbedBuilder e = new EmbedBuilder().setTitle("MUG 인증 시스템");
            boolean isError = false;
            String Error = "";
            if((list.containsKey(op) && !Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("828077971591004251")))){
                for (Thread t : Thread.getAllStackTraces().keySet())
                    if (t.getName().equals(op)) {
                        t.interrupt();
                    }
                new save().add(list.get(op), event.getUser().getId());
                try{
                    Player p = Bukkit.getPlayer(list.get(op));
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            World w = Bukkit.getWorld("tutorial");
                            p.teleport(w.getSpawnLocation());
                            p.sendMessage("§a[!] §f정상적으로 인증되었습니다.");
                            p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                    }.runTask(Bukkit.getPluginManager().getPlugin("Asdfdasfadsf"));
                    e.addField("✅ 인증이 성공적으로 되었습니다!", "마인크래프트 uuid: " + list.get(op), false).setColor(Color.GREEN);
                }catch (NullPointerException e1){}
                catch (Exception e1){
                    e.addField("❌ 오류", "불편에 끼쳐 죄송합니다. 인증을 처리하는 도중 오류가 발생하였습니다. 개선을 위해, 오류제보를 해주시길 바랍니다.", false).setColor(Color.RED);
                    isError = true;
                    Error = e1.toString();
                }

            }else if(!list.containsKey(op)){
                isError = true;
                e.addField("❌ 해당 코드는 만료되었거나, 잘못된 코드 입니다.", "입력된 값: " + op, false).setColor(Color.RED);
            }else if(Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("828077971591004251"))){
                isError = true;
                e.addField("❌ 당신은 이미 인증이 되어있습니다", "입력된 값: " + op, false).setColor(Color.RED);
            }
            boolean finalIsError = isError;
            String finalError = Error;
            event.replyEmbeds(e.build()).queue(s -> {
                if(finalIsError && !finalError.equals("")){
                    s.sendMessage("```java\n"+ finalError +"\n```").queue();
                }else if(!finalIsError){
                    try {
                        event.getMember().modifyNickname("✅ "+ JsonReader.ReadFromUrl("https://api.mojang.com/user/profiles/"+list.get(op).toString().replace("-", "")+"/names").get("name")).queue();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equals("!reupload") && event.getAuthor().getId().equals("719932404877230140")){
            event.getGuild().upsertCommand("인증", "서버와 연동하여, 인증합니다.").addOption(OptionType.STRING, "코드", "코드로 인증합니다.", true).queue();
        }
    }

    //    @Override
//    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
//        String[] args = event.getMessage().getContentRaw().split(" ");
//        if(event.getMessage().getContentDisplay().startsWith("!인증")){
//            EmbedBuilder e = new EmbedBuilder().setTitle("MUG 인증 시스템");
//            try {
//                if ((list.containsKey(args[1]) && args.length == 2 && !Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("828077971591004251")))) {
//                    for (Thread t : Thread.getAllStackTraces().keySet())
//                        if (t.getName().equals(args[1]))
//                            t.interrupt();
//                    new save().add(list.get(args[1]), event.getAuthor().getId());
//                    Player p = Bukkit.getPlayer(list.get(args[1]));
//                    try {
//                        new BukkitRunnable(){
//                            @Override
//                            public void run(){
//                                World w = Bukkit.getWorld("tutorial");
//                                p.teleport(w.getSpawnLocation());
//                            }
//                        }.runTask(Bukkit.getPluginManager().getPlugin("Asdfdasfadsf"));
//                    } catch (NullPointerException e1) {
//                        Asdfdasfadsf.s.createWorld(new WorldCreator("tutorial"));
//                        World w = Bukkit.getWorld("tutorial");
//                        p.teleport(w.getSpawnLocation());
//                    }
//                    p.sendMessage("§a[!] §f정상적으로 인증되었습니다.");
//                    new BukkitRunnable(){
//                        @Override
//                        public void run() {
//                            p.removePotionEffect(PotionEffectType.INVISIBILITY);
//                        }
//                    }.runTask(Bukkit.getPluginManager().getPlugin("Asdfdasfadsf"));
//
//                    event.getMember().modifyNickname("✅ "+ JsonReader.ReadFromUrl("https://api.mojang.com/user/profiles/"+list.get(args[1]).toString().replace("-", "")+"/names").get("name")).queue();
//
//                    Role user = event.getGuild().getRoleById("821696844958007347");
//                    Role in = event.getGuild().getRoleById("828077971591004251");
//
//                    event.getGuild().addRoleToMember(event.getAuthor().getId(), Objects.requireNonNull(in)).queue();
//                    event.getGuild().removeRoleFromMember(event.getAuthor().getId(), Objects.requireNonNull(user)).queue();
//                    e.addField("✅ 인증이 성공적으로 되었습니다!", "마인크래프트 uuid: " + list.get(args[1]), false).setColor(Color.GREEN);
//                    list.remove(args[1]);
//                } else if (Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("828077971591004251"))) {
//                    e.addField("❌ 당신은 이미 인증이 되어있습니다", "입력된 값: " + args[1], false).setColor(Color.RED);
//                } else if (!list.containsKey(args[1])) {
//                    e.addField("❌ 해당 코드는 만료되었거나, 잘못된 코드 입니다.", "입력된 값: " + args[1], false).setColor(Color.RED);
//                } else {
//                    e.addField("❌ 해당 코드는 만료되었거나, 잘못된 코드 입니다.", "입력된 값: " + args[1], false).setColor(Color.RED);
//                }
//            }catch (ArrayIndexOutOfBoundsException e1){
//                e.addField("사용방법: ", "!인증 <인증번호>", false).setColor(Color.RED);
//            } catch (IOException ioException) {
//                Asdfdasfadsf.l.warning(ioException.toString());
//            }
//            event.getChannel().sendMessageEmbeds(e.build()).queue();
//        }
//    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild g =event.getGuild();
        g.loadMembers((Consumer<Member>) g.getMembers()).onSuccess(success -> {
            Asdfdasfadsf.l.info("맴버 로드 성공");
        });
    }
}
