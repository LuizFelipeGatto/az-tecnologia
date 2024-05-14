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
                    el-input(v-model="form.nome" placeholder="digite o nome da unidade" maxlength="128" @blur="handleBlur" :class="{ 'error': hasErrorNome }")
                    p(v-if="hasErrorNome" class="error-message") Campo obrigatório!
                    
            el-col(:span="24")
                el-form-item
                    el-button(type="primary" @click="onSubmit") {{this.id !== null ? 'Salvar' : 'Cadastrar'}}
                    el-button Cancelar
</template>

<script>

import api from '../../services/api.js';
import { RouterLink } from 'vue-router';
import { ElMessage } from 'element-plus';

export default {
    name: 'unidadeVue',

    data () {
        return {
            id: null,
            form: {
                nome: '',
            },
            blurred: false
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

    computed: {
        hasErrorNome() {
            return this.form.nome === '' && this.blurred;
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
            const retorno = await api.salvar('unidade', this.form);
            if(retorno.mensagemErro !== null){
                this.mensagem('warning', retorno.mensagemErro)
            } else {
                this.mensagem('success', retorno.resultado)
                this.form.nome = ''
                this.blurred = false;
            }
        },

        async atualizar(id) {
            const retorno = await api.atualizar('unidade', this.form, id);
            if(retorno.mensagemErro !== null){
                this.mensagem('warning', retorno.mensagemErro)
            } else {
                this.mensagem('success', retorno.resultado)
                this.form.nome = ''
                this.blurred = false;
            }
        },

        mensagem(tipo, mensagem) {
            ElMessage({
                    type: tipo,
                    message: mensagem,
            });
        },

        handleBlur() {
            this.blurred = true;
        },
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
