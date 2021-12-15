package com.eegets

/**
 * Created by wangkai on 2021/12/13 16:51
 * <p>
 * Desc 富文本消息格式
 * https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN#f62e72d5
 */
data class FeishuMsgBean(
        val msg_type: String,
        val content: Content
) {

    data class Content(
            val post: Post
    )

    data class Post(
            val zh_cn: ZhCn
    )

    data class ZhCn(
            val content: MutableList<List<ContentX>>,
            val title: String
    )

    data class ContentX(

            val href: String? = "",
            var tag: String? = null,
            var text: String? = null,
            val user_id: String? = ""
    )
}
