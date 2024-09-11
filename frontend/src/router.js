
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import HospitalizationinfoHospitalizationInfoManager from "./components/listers/HospitalizationinfoHospitalizationInfoCards"
import HospitalizationinfoHospitalizationInfoDetail from "./components/listers/HospitalizationinfoHospitalizationInfoDetail"

import HospitalHospitalManager from "./components/listers/HospitalHospitalCards"
import HospitalHospitalDetail from "./components/listers/HospitalHospitalDetail"

import PatientPatientManager from "./components/listers/PatientPatientCards"
import PatientPatientDetail from "./components/listers/PatientPatientDetail"

import ReservationReservationManager from "./components/listers/ReservationReservationCards"
import ReservationReservationDetail from "./components/listers/ReservationReservationDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/hospitalizationinfos/hospitalizationInfos',
                name: 'HospitalizationinfoHospitalizationInfoManager',
                component: HospitalizationinfoHospitalizationInfoManager
            },
            {
                path: '/hospitalizationinfos/hospitalizationInfos/:id',
                name: 'HospitalizationinfoHospitalizationInfoDetail',
                component: HospitalizationinfoHospitalizationInfoDetail
            },

            {
                path: '/hospitals/hospitals',
                name: 'HospitalHospitalManager',
                component: HospitalHospitalManager
            },
            {
                path: '/hospitals/hospitals/:id',
                name: 'HospitalHospitalDetail',
                component: HospitalHospitalDetail
            },

            {
                path: '/patients/patients',
                name: 'PatientPatientManager',
                component: PatientPatientManager
            },
            {
                path: '/patients/patients/:id',
                name: 'PatientPatientDetail',
                component: PatientPatientDetail
            },

            {
                path: '/reservations/reservations',
                name: 'ReservationReservationManager',
                component: ReservationReservationManager
            },
            {
                path: '/reservations/reservations/:id',
                name: 'ReservationReservationDetail',
                component: ReservationReservationDetail
            },



    ]
})
