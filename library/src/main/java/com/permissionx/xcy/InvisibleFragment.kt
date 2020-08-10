package com.permissionx.xcy

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment


// 创建一个隐藏的 Fragment 用于 对运行时权限的API进行封装
//不用担心 Fragment 对 Activity的性能产生影响

//typealias 可以给任何类别指定一个 别名
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    //定义一个变量 作为运行时 权限申请结果的回调通知方式
    private var callback: PermissionCallback? = null

    //接受一个 callback相同类型的参数 和一个可变长度 permission参数列表
    fun requestNow(cb: PermissionCallback, vararg permission: String) {

        callback = cb
        //通过 Fragment提供的方法 申请运行时权限 并将参数列表传入
        requestPermissions(permission,1)
    }

    //重写请求方法
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {

            //定义一个被用户拒绝的申请权限列表
            val deniedList = ArrayList<String>()
            //遍历 将未授权的加入列表
            for ((index, result) in grantResults.withIndex()) {

                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            //判断是否所有权限申请是否被授权
            val allGranted = deniedList.isEmpty()
            //对权限的申请结果 回调
            callback?.let { it(allGranted, deniedList) }
        }
    }
}