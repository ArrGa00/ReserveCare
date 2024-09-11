<template>

    <v-data-table
        :headers="headers"
        :items="getNameById"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'GetNameByIdView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            getNameById : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/getNameByIds'))

            temp.data._embedded.getNameByIds.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.getNameById = temp.data._embedded.getNameByIds;
        },
        methods: {
        }
    }
</script>

