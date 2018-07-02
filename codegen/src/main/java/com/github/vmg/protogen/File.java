package com.github.vmg.protogen;

import com.github.vmg.protogen.types.AbstractType;
import com.squareup.javapoet.ClassName;

import java.util.*;

public class File {
    public static String PROTO_SUFFIX = "Pb";

    private ClassName baseClass;
    private Element message;
    private String filePath;

    public File(Class object) {
        String className = object.getSimpleName() + PROTO_SUFFIX;
        this.filePath = "model/" + object.getSimpleName().toLowerCase() + ".proto";
        this.baseClass = ClassName.get(ProtoGen.PROTO_JAVA_PACKAGE_NAME, className);
        this.message = new Message(object,  AbstractType.baseClass(baseClass, filePath));
    }

    public String getJavaClassName() {
        return baseClass.simpleName();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getPackageName() {
        return ProtoGen.PROTO_PACKAGE_NAME;
    }

    public String getJavaPackageName() {
        return ProtoGen.PROTO_JAVA_PACKAGE_NAME;
    }

    public String getGoPackage() {
        return ProtoGen.PROTO_GO_PACKAGE_NAME;
    }

    public Element getMessage() {
        return message;
    }

    public Set<String> getIncludes() {
        Set<String> includes = new HashSet<>();
        message.findDependencies(includes);
        includes.remove(this.getFilePath());
        return includes;
    }
}
