/**
 * @desc 显示菜单
 */
function showMenu() {
  const prompt = require('@system.prompt')
  const itemFuncMapping = [
    createShortcut,
    call3thPartyShare,
    jump2AboutPage,
    null
  ]
  prompt.showContextMenu({
    itemList: ['保存桌面', '分享', '关于', '取消'],
    success: function(ret) {
      if (itemFuncMapping[ret.index]) {
        itemFuncMapping[ret.index]()
      } else {
        prompt.showToast({
          message: 'error'
        })
      }
    }
  })
}

/**
 * @desc 跳转至 About 页面
 */
function jump2AboutPage() {
  const router = require('@system.router')
  const appInfo = require('@system.app').getInfo()
  router.push({
    uri: '/pages/About',
    params: {
      name: appInfo.name,
      icon: appInfo.icon
    }
  })
}

/**
 * @desc 调起第三方分享
 */
function call3thPartyShare() {
  const share = require('@service.share')

  share.share({
    shareType: 0,
    title: '快应用',
    summary: '快应用是基于手机硬件平台的新型应用形态',
    imagePath: '/assets/images/logo.png',
    targetUrl: 'https://www.quickapp.cn/',
    platforms: ['WEIBO', 'WEIXIN', 'WEIXIN_CIRCLE'],
    success: function(data) {
      console.log(`handling success ${data}`)
    },
    fail: function(data, code) {
      console.log(`handling fail, code = ${code}`)
      console.log(data)
    }
  })
}

/**
 * @desc 创建桌面图标
 * 注意：使用加载器测试`创建桌面快捷方式`功能时，请先在`系统设置`中打开`应用加载器`的`桌面快捷方式`权限
 */
function createShortcut() {
  const prompt = require('@system.prompt')
  const shortcut = require('@system.shortcut')
  shortcut.hasInstalled({
    success: function(ret) {
      if (ret) {
        prompt.showToast({
          message: '已创建桌面图标'
        })
      } else {
        shortcut.install({
          success: function() {
            prompt.showToast({
              message: '成功创建桌面图标'
            })
          },
          fail: function(errmsg, errcode) {
            prompt.showToast({
              message: `${errcode}: ${errmsg}`
            })
          }
        })
      }
    }
  })
}

function queryString(url, query) {
  let str = []
  for (let key in query) {
    str.push(key + '=' + query[key])
  }
  let paramStr = str.join('&')
  return paramStr ? `${url}?${paramStr}` : url
}

export default {
  showMenu,
  createShortcut,
  queryString
}
