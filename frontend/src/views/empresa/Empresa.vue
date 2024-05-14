<template lang="pug">
  main
    div(class="titulo")
        RouterLink(:to="'/empresas'") 
            el-button(class="btn-primary") Listar empresas
        h2 {{this.id === null ? 'Cadastro' : 'Edição'}} de empresas
    el-form(:model="form" label-width="auto")
        el-row(:gutter="10")
            el-col(:span="12")
                el-form-item(label="Razão Social")
                    el-input(v-model="form.razaoSocial" placeholder="digite a razão social" maxlength="64" @blur="handleBlurRazao" :class="{ 'error': hasErrorRazao }")
                    p(v-if="hasErrorRazao" class="error-message") Campo obrigatório!
            el-col(:span="12")
                el-form-item(label="CNPJ")
                    el-input(v-model="form.cnpj" placeholder="digite o cnpj" v-mask="'##.###.###/####-##'" maxlength="32" @blur="handleBlurCnpj" :class="{ 'error': hasErrorCnpj }")
                    p(v-if="hasErrorCnpj" class="error-message") Campo obrigatório!
            el-col(:span="12")
                el-form-item(label="Município")
                    el-input(v-model="form.municipio" placeholder="digite o município" maxlength="64")
            el-col(:span="12")
                el-form-item(label="CEP")
                    el-input(v-model="form.cep" placeholder="digite o cep" v-mask="'#####-###'" maxlength="16")
            el-col(:span="12")
                el-form-item(label="Logradouro")
                    el-input(v-model="form.lograduro" placeholder="digite o logradouro" maxlength="64")
            el-col(:span="12")
                el-form-item(label="Bairro")
                    el-input(v-model="form.bairro" placeholder="digite o bairro" maxlength="64")
            el-col(:span="6")
                el-form-item(label="Número")
                    el-input(v-model="form.numero" placeholder="digite o número" maxlength="10")
            el-col(:span="6")
                el-form-item(label="Complemento")
                    el-input(v-model="form.complemento" placeholder="digite o complemento" maxlength="64")
            el-col(:span="12")
                el-form-item(label="Telefone")
                    el-input(v-model="form.telefone" placeholder="(xx) xxxxx-xxxx" v-mask="'(##) #####-####'" maxlength="32")
            el-col(:span="12")
                el-form-item(label="E-mail")
                    el-input(v-model="form.email" placeholder="digite o email" v-validate="'required|email'" @blur="handleBlur" :class="{ 'error': hasError }" maxlength="254")
                    p(v-if="hasError" class="error-message") Campo obrigatório!

            el-col(:span="12")
                el-form-item(label="Site")
                    el-input(v-model="form.site" placeholder="digite o site" maxlength="254")
            el-col(:span="8")
                el-form-item(label="Usuário")
                    el-input(v-model="form.usuario" placeholder="digite o usuario" maxlength="20" @blur="handleBlurUsuario" :class="{ 'error': hasErrorUsuario }")
                    p(v-if="hasErrorUsuario" class="error-message") Campo obrigatório!
            el-col(:span="8" v-if="this.id === null")
                el-form-item(label="Senha")
                    el-input(v-model="form.senha" placeholder="digite a senha" type="password" show-password maxlength="128")
                    
            el-col(:span="24")
                el-form-item
                    el-button(type="primary" @click="onSubmit") {{this.id !== null ? 'Salvar' : 'Cadastrar'}}
                    el-button Cancelar
</template>

<script>

import api from '../../services/api.js';
import { VueMaskDirective } from 'vue-the-mask';
import { RouterLink } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
    name: 'empresaVue',
    directives: { mask: VueMaskDirective },
    
    data () {
        return {
            id: null,
            form: {
                razaoSocial: '',
                cnpj: '',
                lograduro: '',
                municipio: '',
                numero: '',
                complemento: '',
                bairro: '',
                cep: '',
                telefone: '',
                email: '',
                site: '',
                usuario: '',
                senha: ''
            },
            blurred: false,
            blurredCnpj: false,
            blurredRazao: false,
            blurredUsuario: false
        }
    },

    async created (){
        this.id = this.$route.params.id || null;
        if (this.id !== null) {
            this.form = await api.buscarPorId('empresa', this.id);
        }
    },

    components: {
        RouterLink
    },

    computed: {

        hasErrorRazao() {
            return this.form.razaoSocial === '' && this.blurredRazao;
        },

        hasErrorCnpj() {
            return this.form.cnpj === '' && this.blurredCnpj;
        },

        hasError() {
            return this.form.email === '' && this.blurred;
        },

        hasErrorUsuario() {
            return this.form.usuario === '' && this.blurredUsuario;
        }
    },

    methods: {
        onSubmit () {
            if(!this.validaCampos()){
                return;
            }
            
            if(this.id !== null){
                this.atualizar(this.id);
            } else{
                this.salvar();
            }
        },

        validateEmail(email) {
            const re = /\S+@\S+\.\S+/;
            return re.test(email);
        },

        validaCampos () {
            if(this.form.razaoSocial === ''){
                this.emitirNotificacao('Verifique a razão social')
                return false;
            }

            if(this.form.cnpj === ''){
                this.emitirNotificacao('Verifique o cnpj')
                return false;
            }


            if(this.form.email === ''){
                this.emitirNotificacao('Verifique o e-mail')
                return false;
            }


            if (!this.validateEmail(this.form.email)) {
                this.emitirNotificacao('E-mail inválido')
                return false;
            }

            if(this.form.usuario === ''){
                this.emitirNotificacao('Verifique o usuário')
                return false;
            }
            
            return true;
        },

        emitirNotificacao (mensagem) {
            ElMessageBox.alert(
                    mensagem,
                    'Atenção',
                    {
                    confirmButtonText: 'Ok',
                    type: 'warning',
                    }
                )
        },

        limpaCampos () {
            this.form = {
                razaoSocial: '',
                cnpj: '',
                lograduro: '',
                municipio: '',
                numero: '',
                complemento: '',
                bairro: '',
                cep: '',
                telefone: '',
                email: '',
                site: '',
                usuario: '',
                senha: ''
            },
            this.blurred = false,
            this.blurredCnpj = false,
            this.blurredRazao = false,
            this.blurredUsuario = false
        },

        async salvar() {
            const retorno = await api.salvar('empresa', this.form);
            if(retorno.mensagemErro !== null){
                this.mensagem('warning', retorno.mensagemErro)
            } else {
                this.mensagem('success', retorno.resultado)
                this.limpaCampos()
            }
        },

        async atualizar(id) {
            const retorno = await api.atualizar('empresa', this.form, id);
            if(retorno.mensagemErro !== null){
                this.mensagem('warning', retorno.mensagemErro)
            } else {
                this.mensagem('success', retorno.resultado)
                this.limpaCampos()
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

        handleBlurCnpj() {
            this.blurredCnpj = true;
        },

        handleBlurRazao() {
            this.blurredRazao = true;
        },

        handleBlurUsuario() {
            this.blurredUsuario = true;
        }
    }
}


</script>

<style>

.titulo {
    margin-bottom: 50px;
}
.error {
    border: 1px solid red;
}

.error-message {
  color: red;
}

@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
