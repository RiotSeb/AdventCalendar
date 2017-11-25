package de.riotseb.adventcalendar.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class ItemBuilder {

    private ItemStack itemstack;


    public ItemBuilder() {
    }

    public ItemBuilder(Material material, Integer amount, Byte data) {
        this.itemstack = new ItemStack(material, amount, data);
    }

    public ItemBuilder withLore(String... lore) {
        List<String> loreList = Arrays.asList(lore);

        ItemMeta meta = itemstack.getItemMeta();
        meta.setLore(loreList);
        this.itemstack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder withDisplayName(String displayName) {
        ItemMeta meta = this.itemstack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        this.itemstack.setItemMeta(meta);

        return this;
    }


    public ItemStack build() {

        if (this.itemstack == null) {
            this.itemstack = new ItemStack(Material.DIRT);
        }

        return this.itemstack;
    }


    public ItemStack getPresentHead(String displayname, String... lore) {
        String url = "http://textures.minecraft.net/texture/ef79f376f45cc3623f73c63de1427f7dd2acbaf8e5d7844d94d254924ba290";

        this.itemstack = getSkull(url, displayname, lore);

        return itemstack;
    }


    private ItemStack getSkull(String url, String displayname, String... lore) {

        this.itemstack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);


        ItemMeta skullMeta = this.itemstack.getItemMeta();
        skullMeta.setDisplayName(displayname);
        skullMeta.setLore(Arrays.asList(lore));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.itemstack.setItemMeta(skullMeta);
        return this.itemstack;


    }

}
