import axios from 'axios';

export const URLS = {
    BACKEND: 'http://localhost:8080/'
}

export default {

    async buscar(path) {
        try {
            const { data } = await axios({
                method: 'GET',
                url: URLS.BACKEND + path
            });
            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    },

    async buscarPorId(path, id) {
        try {
            const { data } = await axios({
                method: 'GET',
                url: URLS.BACKEND + path + '/' + id
            });

            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    },

    async salvar(path, form) {
        try {
            const {data} = await axios({
                method: 'POST',
                url: URLS.BACKEND + path,
                data: form
            });
            
            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    },

    async filtro(path, filtro) {
        try {
            const {data} = await axios({
                method: 'POST',
                url: URLS.BACKEND + path + '/filter',
                // headers: {
                //     'Content-Type': 'application/json'
                // },
                data: filtro
            });
            
            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    },

    async atualizar(path, form, id) {
        try {
            const {data} = await axios({
                method: 'PUT',
                url: URLS.BACKEND + path + '/' + id,
                data: form
            });
           
            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    },

    async deletar(path, id) {
        try {
            const {data} = await axios({
                method: 'DELETE',
                url: URLS.BACKEND + path + '/' + id
            });
            
            return data;
        } catch (error) {
            console.error('Error ao buscar jogo e atletas:', error);
        }
    }

};
