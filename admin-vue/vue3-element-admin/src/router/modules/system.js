// 导入组件
const Layout = () => import('@/layout/index.vue')
const sysRole = () => import('@/views/system/sysRole.vue')
const sysUser = () => import('@/views/system/sysUser.vue')
const sysMenu = () => import('@/views/system/sysMenu.vue')

// 导出该组件
export default [
  {
    path: '/system',
    component: Layout,
    name: 'system',
    meta: {
      title: '系统管理',
    },
    icon: 'Location',
    children: [
      {
        path: '/sysRole',
        name: 'sysRole',
        component: sysRole,
        meta: {
          title: '角色管理',
        },
        hidden: false,
      },
      {
        path: '/sysUser',
        name: 'sysUser',
        component: sysUser,
        meta: {
          title: '用户管理',
        },
        hidden: false,
      },
      {
        path: '/menu',
        name: 'sysMenu',
        component: sysMenu,
        meta: {
          title: '菜单管理',
        },
        hidden: false,
      },
    ],
  },
]

// export default([
//     {
//         "title": "系统管理",
//         "name": "system",
//         "children": [
//             {
//                 "title": "用户管理",
//                 "name": "sysUser",
//                 "children": null
//             },
//             {
//                 "title": "角色管理",
//                 "name": "sysRole",
//                 "children": null
//             },
//             {
//                 "title": "菜单管理",
//                 "name": "sysMenu",
//                 "children": null
//             }
//         ]
//     },
//     {
//         "title": "数据管理",
//         "name": "base",
//         "children": [
//             {
//                 "title": "地区管理",
//                 "name": "region",
//                 "children": null
//             },
//             {
//                 "title": "商品单位",
//                 "name": "productUnit",
//                 "children": null
//             }
//         ]
//     }
// ])
