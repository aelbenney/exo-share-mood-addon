import './initComponents.js';
Vue.use(Vuetify);

const vuetify = new Vuetify({
  dark: true,
  iconfont: '',
});

const appId = 'ShareMoodApp';

//should expose the locale ressources as REST API
const lang = (eXo && eXo.env && eXo.env.portal && eXo.env.portal.language) || 'en';
const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/locale.addon.Kudos-${lang}.json`;

export function init() {
  exoi18n.loadLanguageAsync(lang, url).then(i18n => {
    new Vue({
      template: `<share-mood id="${appId}" />`,
      vuetify,
      i18n
    }).$mount(`#${appId}`);
  });
}
