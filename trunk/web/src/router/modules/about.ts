import { RouteRecordRaw } from 'vue-router';
import { Layout, ParentLayout } from '@/router/constant';
import { renderNew } from '@/utils/index';

const routeName = 'comp';
const routes: Array<RouteRecordRaw> = [
  {
    path: '/dev',
    name: 'dev',
    component: Layout,
    meta: {
      title: '开发者',
      sort: 10,
      isRoot: true,
      activeMenu: 'about_index',
    },
    children: [
      {
        path: '/comp',
        name: routeName,
        component: ParentLayout,
        redirect: '/comp/table',
        meta: {
          title: '组件示例',
          sort: 8,
        },
        children: [
          {
            path: 'table',
            name: `${routeName}_table`,
            redirect: '/comp/table/basic',
            component: ParentLayout,
            meta: {
              title: '表格',
            },
            children: [
              {
                path: 'basic',
                name: `${routeName}_table_basic`,
                meta: {
                  title: '基础表格',
                },
                component: () => import('@/views/comp/table/basic.vue'),
              },
              {
                path: 'editCell',
                name: `${routeName}_table_editCell`,
                meta: {
                  title: '单元格编辑',
                },
                component: () => import('@/views/comp/table/editCell.vue'),
              },
              {
                path: 'editRow',
                name: `${routeName}_table_editRow`,
                meta: {
                  title: '整行编辑',
                },
                component: () => import('@/views/comp/table/editRow.vue'),
              },
            ],
          },
          {
            path: 'form',
            name: `${routeName}_form`,
            redirect: '/comp/form/basic',
            component: ParentLayout,
            meta: {
              title: '表单',
            },
            children: [
              {
                path: 'basic',
                name: `${routeName}_form_basic`,
                meta: {
                  title: '基础使用',
                },
                component: () => import('@/views/comp/form/basic.vue'),
              },
              {
                path: 'useForm',
                name: `useForm`,
                meta: {
                  title: 'useForm',
                },
                component: () => import('@/views/comp/form/useForm.vue'),
              },
            ],
          },
          {
            path: 'upload',
            name: `${routeName}_upload`,
            meta: {
              title: '上传图片',
            },
            component: () => import('@/views/comp/upload/index.vue'),
          },
          {
            path: 'modal',
            name: `${routeName}_modal`,
            meta: {
              title: '弹窗扩展',
            },
            component: () => import('@/views/comp/modal/index.vue'),
          },
          {
            path: 'richtext',
            name: `richtext`,
            meta: {
              title: '富文本',
            },
            component: () => import('@/views/comp/richtext/vue-quill.vue'),
          },
          {
            path: 'drag',
            name: `Drag`,
            meta: {
              title: '拖拽',
            },
            component: () => import('@/views/comp/drag/index.vue'),
          },
        ],
      },
      {
        path: 'index',
        name: `directive_index`,
        meta: {
          title: '指令示例',
          activeMenu: 'directive_index',
        },
        component: () => import('@/views/directive/index.vue'),
      },
      {
        path: 'index',
        name: `about_index`,
        meta: {
          title: '关于项目',
          activeMenu: 'about_index',
        },
        component: () => import('@/views/about/index.vue'),
      },
      {
        path: '/external',
        name: 'https://docs.naiveadmin.com',
        component: Layout,
        meta: {
          title: '项目文档',
          sort: 11,
        },
      },
      {
        path: 'naive',
        name: 'frame-naive',
        meta: {
          title: 'NaiveUi(内嵌)',
          frameSrc: 'https://www.naiveui.com',
        },
        component: Layout,
      },
    ],
  },
];

export default routes;
