<template lang="pug">
  main
    div(class="titulo")
        h2 Pesquisa de empresas
    .search-form
        el-row(:gutter="30")
            el-col(:span="8")
                el-form-item(label="Razão Social")
                    el-select(
                        v-model="filtro.comprador"
                        placeholder="Selecione a empresa"
                        :value-on-clear="null"
                        clearable
                    )
                        el-option(
                            v-for="item in empresasSelect"
                            :key="item.id"
                            :label="item.razaoSocial"
                            :value="item.id"
                        )
            el-col(:span="6")
                el-form-item(label="CNPJ")
                    el-input(type="text" v-model="filtro.cnpj" placeholder="00.000.000/0000-00" v-mask="'##.###.###/####-##'")
            el-col(:span="8")
                el-form-item(label="Telefone")
                    el-input(type="text" v-model="filtro.telefone" placeholder="(xx) xxxxx-xxxx" v-mask="'(##) #####-####'")
            el-col(:span="8")
                el-form-item(label="E-mail")
                    el-input(type="text" v-model="filtro.email" placeholder="digite o e-mail")
        el-button(class="btn-outline-success" @click="submitForm") Pesquisar


    el-row(:gutter="30")
        el-col(:span="12")
            div(class="titulo")
                h2 Listagem de empresas
        el-col(:span="12" class="margem-bottom")
             RouterLink(:to="'/empresa'")
                el-button(class="btn-success") + Cadastrar
        el-col(:span="24")
            el-table.tabela(
                :data="displayedItems"
                style="width: 100%;"
                border
                empty-text="Sem dados"
            )
                el-table-column(prop="cnpj" label="CNPJ" )
                el-table-column(prop="razaoSocial" label="Razão Social" )
                el-table-column(prop="telefone" label="Telefone")
                el-table-column(prop="email" label="E-mail")
                el-table-column(label="Ações")
                    template(v-slot="scope")
                        RouterLink(:to="'/empresa/' + scope.row.id")
                            el-tooltip(
                                class="box-item"
                                effect="dark"
                                content="Editar"
                                placement="left-start"
                            )
                                el-button(class="btn-primary" size="small")
                                    font-awesome-icon(icon="edit")
                        el-button(@click="open(scope.row.id, scope.row.nome)" class="btn-danger" size="small")
                            font-awesome-icon(icon="trash")
                
        el-col(:span="24" class="margem")
            el-pagination(
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20, 30, 40]"
                :page-size="pageSize"
                background 
                layout="prev, pager, next, total" 
                :total="totalItems"
            )

</template>

<script>

import api from '../../services/api.js';
import { RouterLink } from 'vue-router';
import { VueMaskDirective } from 'vue-the-mask';
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
    name: 'empresasVue',
    directives: { mask: VueMaskDirective },

    components: {
        RouterLink
    },

    data () {
        return {
            filtro: {
                cnpj: '',
                razaoSocial: '',
                telefone: '',
                email: '',
                comprador: null
            },
            currentPage: 1,
            pageSize: 5,
            empresas: [],
            empresasSelect: [],
            empresa: {}
        }
    },

    async created () {
       this.empresas = await api.buscar('empresa'); 
       this.empresasSelect = await api.buscar('empresa');
    },

    computed: {
        displayedItems() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            return this.empresas.slice(startIndex, endIndex);
        },

        totalItems() {
            return this.empresas.length;
        },
    },

    methods: {

        handleCurrentChange(page) {
            this.currentPage = page;
        },

        async submitForm() {
           const retorno = await api.filtro('empresa', this.filtro)
           this.empresas = retorno.content || retorno
        },

        open(id) {
            ElMessageBox.confirm(
                'Deseja realmente excluir este item ?',
                'Atenção',
                {
                confirmButtonText: 'Sim',
                cancelButtonText: 'Cancelar',
                type: 'warning',
                }
            )
            .then(() => {
                this.handleOk(id);
            })
            .catch(() => {
                
            });
        },

        async handleOk(id) {
            const retorno = await api.deletar('empresa', id);
            if(retorno.mensagemErro !== null){
                this.mensagem('info', retorno.mensagemErro)
            } else {
                this.empresas = this.empresas.filter(item => item.id !== id);
                this.mensagem('success', retorno.resultado)
            }
        },

        mensagem(tipo, mensagem) {
            ElMessage({
                    type: tipo,
                    message: mensagem,
            });
        }
    }
}


</script>

<style>

.titulo {
    margin-bottom: 20px;
}

.btn-success {
    background-color: #009231;
    border-color: #009231;
    color: #fff;
}

.btn-primary {
    background-color: #005892;
    border-color: #005892;
    color: #fff;
}

.btn-danger {
    background-color: #cf3b00;
    border-color: #cf3b00;
    color: #fff;
}

.btn-outline-success {
    background-color: #ffffff;
    border-color: #009231;
    color: #009231;
}

.btn-outline-success:hover {
    background-color: #009231;
    border-color: #009231;
    color: #fff;
}

.btn-success:hover {
    background-color: #00551c;
    border-color: #00551c;
    color: #fff;
}


.search-form {
  margin-bottom: 20px;
}

.margem {
    margin-top: 15px;
}

.margem-bottom {
    margin-bottom: 20px;
}


@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
