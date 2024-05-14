<template lang="pug">
  div(class="common-layout")
    el-header
      nav
        el-menu(
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
        )
          el-menu-item
            RouterLink(to="/") Home
          el-menu-item
            RouterLink(to="/empresas") Empresas
          el-menu-item
            RouterLink(to="/leiloes") Leilões
          el-menu-item
            RouterLink(to="/unidades") Unidades
    el-container(class="margem")
      el-main
          div(class="wrapper")
            RouterView

</template>

<script>
import { RouterLink, RouterView } from 'vue-router'

export default {

  components: {
    RouterLink,
    RouterView
  }

}


</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.margem {
  margin-top: 100px;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
  color: #000 !important;
}

nav a.router-link-exact-active {
  color: #000;
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  /* border-left: 1px solid var(--color-border); */
}

nav a:first-of-type {
  border: 0;
}

.el-header {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000; /* Certifique-se de que o z-index é alto o suficiente para estar sempre acima do conteúdo */
}


@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
