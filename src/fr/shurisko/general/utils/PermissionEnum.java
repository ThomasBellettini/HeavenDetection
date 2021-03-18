package fr.shurisko.general.utils;

public enum PermissionEnum {

    OWNER("heaven.owner"),
    ADMIN("heaven.admin"),
    MOD("heaven.mod"),
    MEMBER("heaven.member"),
    FRIEND("heaven.friend"),
    OTHER("heaven.other");

    public String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
