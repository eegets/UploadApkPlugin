package com.eegets

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory


/**
 * Created by wangkai on 2021/12/22 16:31

 * Desc build.gradle配置Pgyer和飞书的配置的参数
 *
 *  BuildParams {
 *      pgyer {
 *
 *      }
 *
 *      feishu {
 *
 *      }
 *  }
 */
open class BuildParams constructor(objectFactory: ObjectFactory) {

    var pgyer: Pgyer? = Pgyer()
    var feishu: Feishu? = Feishu()
    init {
        pgyer = objectFactory.newInstance(Pgyer::class.java)
        feishu = objectFactory.newInstance(Feishu::class.java)
    }

    fun pgyer(action: Action<Pgyer>) {
        action.execute(pgyer)
    }

    fun feishu(action: Action<Feishu>) {
        action.execute(feishu)
    }

    open class Pgyer {
        var _api_key: String = ""
        var appKey: String = ""
        var userKey: String = ""
    }

    open class Feishu {
        var hookUrl: String = ""
    }

}