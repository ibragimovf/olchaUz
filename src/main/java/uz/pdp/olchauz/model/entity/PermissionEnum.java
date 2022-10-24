package uz.pdp.olchauz.model.entity;

public enum PermissionEnum {
    CATEGORY_ADD("category::add"),
    CATEGORY_DELETE("category::delete"),
    CATEGORY_EDIT("category::edit"),

    PRODUCT_ADD("product::add"),
    PRODUCT_DELETE("product::delete"),
    PRODUCT_EDIT("product::edit");
    private String value;
    PermissionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
