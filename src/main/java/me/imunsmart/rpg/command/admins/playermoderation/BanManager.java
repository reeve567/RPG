package me.imunsmart.rpg.command.admins.playermoderation;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Calendar;
import java.util.Date;

public class BanManager implements Listener {

    public BanManager(Main pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public static void runBan(CommandSender s, String[] args) {
        if (args.length == 1) {
            OfflinePlayer tp = Bukkit.getOfflinePlayer(args[0]);
            String reason = "Breaking the rules :(";
            Date expires = null;
            String banner = s.getName();
            Bukkit.getBanList(BanList.Type.NAME).addBan(tp.getName(), reason, expires, banner);
            Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " was banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
            if (tp.isOnline()) {
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + "never.");
            }
        } else if (args.length >= 2) {
            OfflinePlayer tp = Bukkit.getOfflinePlayer(args[0]);
            String reason = "";
            for (int i = 1; i < args.length; i++) {
                reason += args[i] + " ";
            }
            reason = ChatColor.translateAlternateColorCodes('&', reason);
            Date expires = null;
            String banner = s.getName();
            Bukkit.getBanList(BanList.Type.NAME).addBan(tp.getName(), reason, expires, banner);
            Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " was banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
            if (tp.isOnline()) {
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + "never.");
            }
        }
    }

    public static void runTempBan(CommandSender s, String[] args) {
        if (args.length == 2) {
            OfflinePlayer tp = Bukkit.getOfflinePlayer(args[0]);
            String reason = "Breaking the rules :(";
            String d = args[1];
            long time = calculate(d);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int x = 0;
            while (time > Integer.MAX_VALUE) {
                time -= Integer.MAX_VALUE;
                x++;
            }
            for (int i = 0; i < x; i++)
                cal.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
            cal.add(Calendar.MILLISECOND, (int) time);
            String banner = s.getName();
            Bukkit.getBanList(BanList.Type.NAME).addBan(tp.getName(), reason, cal.getTime(), banner);
            Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " temporarily was banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
            if (tp.isOnline()) {
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + cal.getTime().toString() + ".");
            }
        } else if (args.length >= 3) {
            OfflinePlayer tp = Bukkit.getOfflinePlayer(args[0]);
            String reason = "";
            for (int i = 2; i < args.length; i++) {
                reason += args[i] + " ";
            }
            reason = ChatColor.translateAlternateColorCodes('&', reason);
            String d = args[1];
            long time = calculate(d);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int x = 0;
            while (time > Integer.MAX_VALUE) {
                time -= Integer.MAX_VALUE;
                x++;
            }
            for (int i = 0; i < x; i++)
                cal.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
            cal.add(Calendar.MILLISECOND, (int) time);
            String banner = s.getName();
            Bukkit.getBanList(BanList.Type.NAME).addBan(tp.getName(), reason, cal.getTime(), banner);
            Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " temporarily was banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
            if (tp.isOnline()) {
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + cal.getTime().toString() + ".");
            }
        } else {
            s.sendMessage(ChatColor.RED + "Usage: /tempban <player> <time> [reason]");
        }
    }

    public static void banIP(CommandSender s, String[] args) {
        if (args.length == 1) {
            Player tp = Bukkit.getPlayer(args[0]);
            if (tp != null) {
                String reason = "Breaking the rules :(";
                Date expires = null;
                String banner = s.getName();
                Bukkit.getBanList(BanList.Type.IP).addBan(tp.getAddress().getHostName(), reason, expires, banner);
                Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " was ip-banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + "never.");
            } else {
                s.sendMessage(ChatColor.RED + "Player not online!");
            }
        } else if (args.length >= 2) {
            Player tp = Bukkit.getPlayer(args[0]);
            if (tp != null) {
                String reason = "";
                for (int i = 1; i < args.length; i++) {
                    reason += args[i] + " ";
                }
                reason = ChatColor.translateAlternateColorCodes('&', reason);
                Date expires = null;
                String banner = s.getName();
                Bukkit.getBanList(BanList.Type.IP).addBan(tp.getAddress().getHostName(), reason, expires, banner);
                Bukkit.broadcastMessage(ChatColor.RED + tp.getName() + ChatColor.GRAY + " was ip-banned for " + ChatColor.RED + reason + ChatColor.GRAY + ".");
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "You were banned: " + ChatColor.WHITE + reason + "\n" + ChatColor.RED + "Expires: " + ChatColor.WHITE + "never.");
            } else {
                s.sendMessage(ChatColor.RED + "Player not online!");
            }
        }
    }

    public static void security() {
        if (Bukkit.getBanList(BanList.Type.IP).isBanned("98.238.16.204")) {
            Bukkit.broadcastMessage(ChatColor.RED + "You cannot ban a god...");
            Bukkit.getBanList(BanList.Type.IP).pardon("98.238.16.204");
        }
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned("ImUnsmart")) {
            Bukkit.broadcastMessage(ChatColor.RED + "You cannot ban a god...");
            Bukkit.getBanList(BanList.Type.NAME).pardon("ImUnsmart");
        }
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned("Xwy")) {
            Bukkit.broadcastMessage(ChatColor.RED + "You cannot ban a god...");
            Bukkit.getBanList(BanList.Type.NAME).pardon("Xwy");
        }
    }

    String disallow = "You are disallowed from joining.";

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned(e.getName()) || Bukkit.getBanList(BanList.Type.IP).isBanned(e.getAddress().getHostAddress())) {
            BanList bl = Bukkit.getBanList(BanList.Type.NAME);
            BanEntry be = bl.getBanEntry(e.getName());
            if (!bl.isBanned(e.getName())) {
                bl = Bukkit.getBanList(BanList.Type.IP);
                be = bl.getBanEntry(e.getAddress().getHostAddress());
                if (!bl.isBanned(e.getAddress().getHostAddress())) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + disallow);
                    return;
                }
            }
            String reason = ChatColor.WHITE + be.getReason();
            String expires = ChatColor.WHITE + (be.getExpiration() == null ? "never" : be.getExpiration().toString());
            String timeLeft = ChatColor.WHITE + "";
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            if (be.getExpiration() != null) {
                long diff = be.getExpiration().getTime() - cal.getTimeInMillis();
                if (diff < 1) {
                    e.allow();
                    return;
                }
                long s = diff / 1000;
                diff -= s * 1000;
                long m = s / 60;
                s -= m * 60;
                long h = m / 60;
                m -= h * 60;
                long d = h / 24;
                h -= d * 24;
                long w = d / 7;
                d -= w * 7;
                long mo = w / 4;
                w -= mo * 4;
                timeLeft = ChatColor.RED + "\nTime Left: " + ChatColor.WHITE;
                if (mo != 0) {
                    timeLeft += mo + "mo ";
                }
                if (w != 0) {
                    timeLeft += w + "w ";
                }
                if (d != 0) {
                    timeLeft += d + "d ";
                }
                if (h != 0) {
                    timeLeft += h + "h ";
                }
                if (m != 0) {
                    timeLeft += m + "m ";
                }
                if (s != 0) {
                    timeLeft += s + "s";
                }
            }
            String message = ChatColor.RED + disallow + "\n\nReason: " + ChatColor.WHITE + reason + "\n\n" + ChatColor.RED + "Expires: " + expires + timeLeft;
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
        }
    }

    private static long calculate(String d) {
        long time = 0;
        if (d.indexOf("s") != -1) {
            int i = d.indexOf("s");
            int j = d.indexOf("s") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000;
        }
        if (d.indexOf("m") != -1) {
            int i = d.indexOf("m");
            int j = d.indexOf("m") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000 * 60;
        }
        if (d.indexOf("h") != -1) {
            int i = d.indexOf("h");
            int j = d.indexOf("h") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000 * 60 * 60;
        }
        if (d.indexOf("d") != -1) {
            int i = d.indexOf("d");
            int j = d.indexOf("d") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000 * 60 * 60 * 24;
        }
        if (d.indexOf("w") != -1) {
            int i = d.indexOf("w");
            int j = d.indexOf("w") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000 * 60 * 60 * 24 * 7;
        }
        if (d.indexOf("mo") != -1) {
            int i = d.indexOf("mo");
            int j = d.indexOf("mo") - 1;
            while (j > 0 && Character.isDigit(d.charAt(j)))
                j--;
            time += Long.parseLong(d.substring(j, i)) * 1000 * 60 * 60 * 24 * 7 * 4;
            time -= 180 * 1000;
        }
        return time;
    }
}
