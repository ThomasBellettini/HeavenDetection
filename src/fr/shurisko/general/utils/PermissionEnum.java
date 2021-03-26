package fr.shurisko.general.utils;

public enum PermissionEnum {

    OWNER("heaven.owner", "§4→ Owner"),
    ADMIN("heaven.admin", "§c→ Administrateur"),
    MOD("heaven.mod", "§9→ Modérateur"),
    MEMBER("heaven.member", "§e→ Membre"),
    FRIEND("heaven.friend", "§d→ Friend"),
    OTHER("heaven.other", "§7→ Aspirant");

    public String permission;
    public String name;

    PermissionEnum(String permission, String name) {
        this.name = name;
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
