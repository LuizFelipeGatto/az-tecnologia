<template lang="pug">
  main
    div(class="titulo")
        h2 Pesquisa de leilões

    .search-form
        el-row(:gutter="10")
            el-col(:span="12")
                el-form-item(label="Razão Social")
                    el-select(
                        v-model="filtro.comprador"
                        placeholder="Selecione o comprador"
                        style="width: 240px"
                        :value-on-clear="null"
                        clearable
                    )
                        el-option(
                            v-for="item in empresas"
                            :key="item.id"
                            :label="item.razaoSocial"
                            :value="item.id"
                        )
            el-col(:span="12")
                el-form-item(label="Total Leilão")
                    el-input-number(type="text" v-model="filtro.totalLeilao")
        el-button(class="btn-outline-success" @click="submitForm") Pesquisar


    el-row(:gutter="30")
        el-col(:span="12")
                div(class="titulo")
                    h2 Listagem de leilões
        el-col(:span="24")
            el-table.tabela(
                :data="displayedItems"
                style="width: 100%;"
                border
                empty-text="Sem dados"
            )
                el-table-column(prop="razaoSocial" label="Razão Social" )
                el-table-column(prop="inicioPrevistoFormatado" label="Início previsto")
                el-table-column(prop="totalLeilao" label="Total leilão" :formatter="formatCurrency")
                
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


export default {
    name: 'leiloesVue',

    data () {
        return {
            filtro: {
                cnpj: '',
                razaoSocial: '',
                telefone: '',
                totalLeilao: null,
                comprador: null,
                email: ''
            },
            valor: false,
            currentPage: 1,
            pageSize: 5,
            leiloes: [],
            empresas: [],
            empresa: {}
        }
    },

    async created () {
       this.leiloes = await api.buscar('leilao'); 
       this.empresas = await api.buscar('empresa');
    },

    computed: {
        displayedItems() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            return this.leiloes.slice(startIndex, endIndex);
        },

        totalItems() {
            return this.leiloes.length;
        },
    },

    methods: {
 
        handleCurrentChange(page) {
            this.currentPage = page;
        },

        formatCurrency(row, column) {
            const value = Number(row[column.property]).toFixed(2);
            return `R$ ${value.replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')}`;
        },

        async submitForm() {
           const retorno = await api.filtro('leilao', this.filtro)
           this.leiloes = retorno.content || retorno
        }
    }
}


</script>

<style>

.search-form {
  margin-bottom: 20px;
}

.margem {
    margin-top: 15px;
}


@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
