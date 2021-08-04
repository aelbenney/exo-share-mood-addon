<template>
  <div id="ShareMoodApp" class="uiBox pa-4">
    <div class="text-header-title text-sub-title pb-4">Share your mood today at work</div>
    <p class="try">Share your </p>
    <div class="emojis">
      <i
        v-for="emoji in emoticons"
        :key="emoji.id"
        class="icons"
        :class="[emoji.iconName,emoji.name, { moodSelected: emoji.isMoodSelected }]"
        @click="selectMood(emoji.id)">
      </i>
    </div>
    <v-spacer />
    <br>
    <v-btn
      v-if="selectedMood"
      class="py-2"
      style="height: 16px"
      depressed
      color="primary">
      {{ moodText }}
    </v-btn>
  </div>
</template>

<script>
export default {
  data: () => ({
    emoticons: [
      {
        id: 0,
        name: 'flaugh',
        iconName: 'mdi mdi-emoticon-excited-outline',
        color: '#00E676',
        isMoodSelected: false,
      },
      {
        id: 1,
        name: 'smile',
        iconName: 'mdi mdi-emoticon-happy-outline',
        color: '#8BC34A',
        isMoodSelected: false,
      },
      {
        id: 2,
        name: 'speechless',
        iconName: 'far fa-grin-hearts',
        color: '#D4E157',
        isMoodSelected: false,
      },
      {
        id: 3,
        name: 'crying',
        iconName: 'mdi mdi-emoticon-cry-outline',
        color: '#FF7043',
        isMoodSelected: false,
      },
      {
        id: 4,
        name: 'sad',
        iconName: 'mdi mdi-emoticon-sad-outline',
        color: '#FF3D00',
        isMoodSelected: false,
      },
    ],
  }),
  computed: {
    selectedMood() {
      return this.emoticons.find(emoji => emoji.isMoodSelected === true)
    },
    moodText() {
      return  `your mood today is ${this.selectedMood.name}`
    }
  },
  methods: {
    selectMood: function (id) {
      if ( this.selectedMood && this.emoticons[id].isMoodSelected === false ) {
        this.selectedMood.isMoodSelected = false;
      }
      this.emoticons[id].isMoodSelected = !this.emoticons[id].isMoodSelected;
    },
  },
}
</script>
