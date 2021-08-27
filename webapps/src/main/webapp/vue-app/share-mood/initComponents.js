import ShareMood from './components/ShareMood.vue';

const components = {
  'share-mood': ShareMood,
};

for (const key in components) {
  Vue.component(key, components[key]);
}

import * as moodService from './js/moodService.js';

if (!Vue.prototype.$moodService) {
  window.Object.defineProperty(Vue.prototype, '$moodService', {
    value: moodService,
  });
}