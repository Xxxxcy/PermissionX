package com.permissionx.xcy

import androidx.fragment.app.FragmentActivity

//编写 对外接口部分

//定义为单例类 方便接口被调用
object PermissionX {

    private const val TAG = "InvisibleFragment"

    fun request(activity: FragmentActivity, vararg permission: String, callback: PermissionCallback) {

        //获取 FragmentActivity的实例
        val fragmentManager = activity.supportFragmentManager
        //判断传入的activity参数是否已经包含指定的Fragment
        val existedFragment = fragmentManager.findFragmentByTag(TAG)

        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        }else{
            //不存在 创建新的实例 添加到activity中并指定TAG
            val invisibleFragment = InvisibleFragment()
            //添加结束后 调用commitNow 会立即执行添加操作 保证下一行代码执行前 添加到Activity中
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        //立即申请权限
        //申请结果回到callback中  *表示 将一个数组转换成可变长度传进
        fragment.requestNow(callback, *permission)
    }
}