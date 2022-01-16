package cf.thdisstudio.asdfdasfadsf;

import cf.thdisstudio.asdfdasfadsf.Discord.Discord;
import cf.thdisstudio.asdfdasfadsf.db.load;
import cf.thdisstudio.asdfdasfadsf.map.chunk;
import net.dv8tion.jda.api.JDA;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.logging.Logger;

public final class Asdfdasfadsf extends JavaPlugin implements Listener {

    public static Map<String, UUID> list = new HashMap<>();
    static final String DB_URL = "jdbc:mariadb://localhost:3306/plugin_manage?useUnicode=true&passwordCharacterEncoding=utf-8";
    public static Connection conn;
    public static Logger l = null;
    public static JDA j;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!new load().isGood(e.getPlayer().getUniqueId())) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1, false, false));
            e.getPlayer().teleport(new Location(Bukkit.getWorld("lobby"), 0.5, 99, 0.5));
            e.getPlayer().sendMessage("\u00A76\u00A7l[!] \u00A7f\uB514\uC2A4\uCF54\uB4DC \uACC4\uC815\uACFC \uC5F0\uB3D9\uD574\uC8FC\uC2DC\uAE38 \uBC14\uB78D\uB2C8\uB2E4.\u00A77(/\uC778\uC99D)");
            e.getPlayer().sendMessage("\u00A7fL \uB514\uC2A4\uCF54\uB4DC \uC11C\uBC84 : https://join.ystbot.cf/join?uuid=mug");
        }else{
            if(e.getPlayer().getWorld().equals(Bukkit.getWorld("lobby"))) {
                if(Bukkit.getWorld("tutorial") == null)
                    Bukkit.createWorld(new WorldCreator("tutorial"));
                World w = Bukkit.getWorld("tutorial");
                e.getPlayer().teleport(w.getSpawnLocation());
                e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
    }

    public static Server s;

    @Override
    public void onEnable() {
        // Plugin startup logic
        s= getServer();
        getLogger().info("마인크래프트 유저 그룹 인증 플러그인을 로딩중입니다...");
        Bukkit.getPluginManager().registerEvents(this , this);
        s.createWorld(new WorldCreator("tutorial"));
        l = getLogger();
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            //STEP 3: Open a connection 08*04*07
            conn = DriverManager.getConnection(
                    DB_URL, "root", "root");
        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName

        if(!(new File(Bukkit.getWorldContainer().getAbsolutePath() + "\\" + "lobby").isDirectory())){
            WorldCreator wc = new WorldCreator("lobby");
            wc.generator(new chunk().getDefaultWorldGenerator());
            l.info("로비 월드 생성중");
            World w = wc.createWorld();
            assert w != null;
            w.setSpawnLocation(0, 99, 0);
            w.getBlockAt(0, 98, 0).setType(Material.BARRIER);

            //1
            w.getBlockAt(-1, 99, 0).setType(Material.BARRIER);
            w.getBlockAt(0, 99, -1).setType(Material.BARRIER);
            w.getBlockAt(0, 99, 1).setType(Material.BARRIER);
            w.getBlockAt(1, 99, 0).setType(Material.BARRIER);

            //2
            w.getBlockAt(-1, 100, 0).setType(Material.BARRIER);
            w.getBlockAt(0, 100, -1).setType(Material.BARRIER);
            w.getBlockAt(0, 100, 1).setType(Material.BARRIER);
            w.getBlockAt(1, 100, 0).setType(Material.BARRIER);
            w.save();
            l.info("로비 월드가 생성되었습니다!");
        }else{
            getServer().createWorld(new WorldCreator("lobby"));
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getWorld("lobby").getPlayers())
                if(new load().isGood(p.getUniqueId()))
                    p.teleport(Bukkit.getWorld("tutorial").getSpawnLocation());
        }, 600L, 200L);

        new Discord().start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        j.shutdownNow();
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("\uC778\uC99D")){
            if(!new load().isGood(((Player) sender).getUniqueId())) {
                Random r = new Random();
                String i = r.nextInt(9) + String.valueOf(r.nextInt(9)) + r.nextInt(9) + String.valueOf(r.nextInt(9));
                list.put(i, ((Player) sender).getUniqueId());
                sender.sendMessage("\u00A76\u00A7l[!] \u00A7f\uC778\uC99D \uC2DC\uC2A4\uD15C");
                sender.sendMessage("\u00A7f\u25B6 \uC778\uC99D\uBC88\uD638 : "+i);
                sender.sendMessage("\u00A7f\u00BB \uB514\uC2A4\uCF54\uB4DC \uC11C\uBC84 \uC778\uC99D\uCC44\uB110\uC5D0 /\uC778\uC99D [\uC778\uC99D\uBC88\uD638]\uB97C \uC785\uB825\uD558\uC138\uC694.");
                sender.sendMessage("    \u00A7f\uD574\uB2F9 \uC778\uC99D\uBC88\uD638\uB294 5\uBD84 \uD6C4 \uB9CC\uB8CC\uB429\uB2C8\uB2E4.");
            }else
                sender.sendMessage("\u00A7c\u00A7l[!] \u00A7f\uB2F9\uC2E0\uC740 \uC774\uBBF8 \uC778\uC99D\uC744 \uD588\uC2B5\uB2C8\uB2E4.  ");
        }

        return false;
    }

}
