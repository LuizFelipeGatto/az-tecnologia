<template lang="pug">
  main
    div(class="titulo")
        RouterLink(:to="'/unidades'")
            el-button(class="btn-primary") Listar unidades
        h2 {{this.id === null ? 'Cadastro' : 'Edição'}} de unidades
    el-form(:model="form" label-width="auto")
        el-row(:gutter="10")
            el-col(:span="12")
                el-form-item(label="Nome")
                    el-input(v-model="form.nome" placeholder="digite o nome da unidade")
                    
            el-col(:span="24")
                el-form-item
                    el-button(type="primary" @click="onSubmit") {{this.id !== null ? 'Salvar' : 'Cadastrar'}}
                    el-button Cancelar
</template>

<script>

import api from '../../services/api.js';
import { RouterLink } from 'vue-router';

export default {
    name: 'unidadeVue',

    data () {
        return {
            id: null,
            form: {
                nome: '',
            }
        }
    },

    components: {
        RouterLink
    },

    async created () {
        this.id = this.$route.params.id || null;
        if (this.id !== null) {
            this.form = await api.buscarPorId('unidade', this.id);
        }
    },

    methods: {

        onSubmit () {
            if(this.id !== null){
                this.atualizar(this.id);
            } else{
                this.salvar();
            }
            
        },

        async salvar() {
            await api.salvar('unidade', this.form);
        },

        async atualizar(id) {
            await api.atualizar('unidade', this.form, id);
        }
    }
}


</script>

<style>

@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
