import ShareMood from './components/ShareMood.vue';

const components = {
  'share-mood': ShareMood,
};

for (const key in components) {
  Vue.component(key, components[key]);
}
