package com.levin.sjf4j.fastjson.ext;

import com.alibaba.fastjson.util.FieldInfo;
import com.jn.langx.annotation.NonNull;
import com.jn.langx.util.Preconditions;

public class FieldInfos {
    public static boolean isMethod(@NonNull FieldInfo info) {
        Preconditions.checkNotNull(info);
        return info.field == null && info.method != null;
    }

    public static boolean isField(@NonNull FieldInfo info) {
        Preconditions.checkNotNull(info);
        return info.field != null;
    }
}
