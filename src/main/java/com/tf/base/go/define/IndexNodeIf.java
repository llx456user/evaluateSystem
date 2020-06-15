package com.tf.base.go.define;

import com.tf.base.go.type.SignType;

public interface IndexNodeIf {
    /**
     * 获取符号[]
     * @return
     */
    SignType getSignType();

    /**
     * 获取设置的数值
     * @return
     */
    String getSettingNum();
}
