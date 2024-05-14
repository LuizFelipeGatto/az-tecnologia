<template lang="pug">
  main
    el-row(:gutter="30")
        el-col(:span="12")
            div(class="titulo")
                h2 Listagem de unidades
        el-col(:span="12" class="margem-bottom")
             RouterLink(:to="'/unidade'")
                el-button(class="btn-success") + Cadastrar
        el-col(:span="24")
            el-table.tabela(
                :data="unidades"
                style="width: 70%;"
                border
                empty-text="Sem dados"
            )
                el-table-column(prop="nome" label="Nome" )
                el-table-column(prop="createdAtFormatada" label="Criado em" )
                el-table-column(prop="updatedAtFormatada" label="Atualizado em")
                el-table-column(label="Ações")
                    template(v-slot="scope")
                        RouterLink(:to="'/unidade/' + scope.row.id")
                            el-button(class="btn-primary")
                                font-awesome-icon(icon="edit")
                        el-button(@click="open(scope.row.id, scope.row.nome)" class="btn-danger")
                            font-awesome-icon(icon="trash")
</template>

<script>
import api from '../../services/api.js';
import { RouterLink } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
    name: 'unidadesVue',

    components: {
        RouterLink
    },

    data () {
        return {
            unidades: [],
            empresa: {}
        }
    },

    async created () {
       this.unidades = await api.buscar('unidade'); 
    },

    methods: {
    
        open(id) {
            ElMessageBox.confirm(
                'Deseja realmente exlcuir este item ?',
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
                this.handleCancel();
            });
        },

        async handleOk(id) {
            try {
                await api.deletar('unidade', id);
                this.unidades = this.unidades.filter(item => item.id !== id);
                ElMessage({
                    type: 'success',
                    message: 'Excluído com sucesso',
                });
            } catch (error) {
                console.error('Erro ao excluir:', error);
            }
        },

        handleCancel() {
            ElMessage({
                type: 'info',
                message: 'Excluído cancelado',
            });
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
