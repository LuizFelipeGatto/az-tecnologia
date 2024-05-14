import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EmpresaView from '../views/empresa/Empresa.vue'
import EmpresasView from '../views/empresa/Empresas.vue'
import LeiloesView from '../views/leilao/Leiloes.vue'
import UnidadeView from '../views/unidade/Unidade.vue'
import UnidadesView from '../views/unidade/Unidades.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/empresa',
      name: 'empresaSalvar',
      component: EmpresaView
    },
    {
      path: '/empresa/:id',
      name: 'empresa',
      component: EmpresaView
    },
    {
      path: '/empresas',
      name: 'empresas',
      component: EmpresasView
    },
    {
      path: '/leiloes',
      name: 'leiloes',
      component: LeiloesView
    },
    {
      path: '/unidade',
      name: 'unidadeSalvar',
      component: UnidadeView
    },
    {
      path: '/unidade/:id',
      name: 'unidade',
      component: UnidadeView
    },
    {
      path: '/unidades',
      name: 'unidades',
      component: UnidadesView
    },
  ]
})

export default router
