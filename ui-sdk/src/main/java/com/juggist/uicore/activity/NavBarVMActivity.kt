package com.juggist.uicore.activity

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.juggist.sdk.activity.BaseVMActivity
import com.juggist.uicore.R
import com.juggist.uicore.activity.ui.theme.White
import com.juggist.uicore.viewmodel.NavViewModel

/**
 * 有导航栏的activity
 */
abstract class NavBarVMActivity<VM : NavViewModel>(private val navVisiable: Boolean = true) :
    BaseVMActivity<VM>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(navVisiable){
            viewModel.showNav()
        }else{
            viewModel.hideNav()
        }
    }

    @Composable
    override fun BaseChildView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffffff))
        ) {
            if (viewModel.navVisiable.observeAsState().value!!)
                NavBarView()
            NavBarChildView()
        }
    }

    /**
     * 创建导航栏视图
     */
    @Composable
    private fun NavBarView() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            val (leftView, centerText, rightView) = createRefs()
            Button(
                modifier = Modifier
                    .constrainAs(leftView) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .width(60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = White,
                    disabledBackgroundColor = White,
                    contentColor = White,
                    disabledContentColor = White
                ), contentPadding = PaddingValues(end = 20.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                onClick = {
                    backClick()
                }
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_black_back),
                    contentDescription = "返回按钮"
                )
            }
            Text("标题", modifier = Modifier.constrainAs(centerText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, fontSize = 15.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Box(modifier = Modifier.constrainAs(rightView) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, 10.dp)
            }) {
                NavRightView()
            }
        }
    }

    /**
     * 导航栏右侧控件，默认空实现
     * 派生类根据自己需求实现
     */
    @Composable
    protected open fun NavRightView() {
    }

    /**
     * 导航栏返回按钮点击事件
     * 派生类可根据自己业务重写
     */
    protected open fun backClick() {
        this.finish()
    }


    /**
     * 导航栏显示/隐藏状态
     */
    fun hideNav() {
        viewModel.hideNav()
    }

    fun showNav() {
        viewModel.showNav()
    }

    /**
     * 创建导航页面的子视图
     * 派生类实现
     */
    @Composable
    abstract fun NavBarChildView()

}