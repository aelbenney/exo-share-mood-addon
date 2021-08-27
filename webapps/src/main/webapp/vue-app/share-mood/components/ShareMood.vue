<template>
  <div id="ShareMoodApp" class="uiBox pa-4">
    <div class="text-header-title text-sub-title pb-3">Share your mood today at work</div>
    <div class="text-color display-2 text-start font-weight-bold">
      <div class="emoticonManager">
        <div v-if="displayForUser">
          <transition name="fade" mode="out-in">
            <div
              v-if="!displayEmoticons"
              key="3"
              class="selected">
              <p>
                Your mood today is {{ moodText }}
                <i
                  key="selectedMood.id"
                  class="icon selectedIcon"
                  :class="[selectedMood.iconName,selectedMood.name] ">
                </i>
                <a
                  href="#"
                  class="change"
                  @click="toggleMoodsForm()">Change</a>
              </p>
            </div>
            <div
              v-if="displayEmoticons"
              key="1"
              class="emojis-form">
              <p>
                Select your mood for today
                <br>
                <i
                  v-for="emoji in emoticons"
                  :key="emoji.id"
                  class="icon"
                  :class="[emoji.iconName,emoji.name, { moodSelected: emoji.isMoodSelected }] "
                  @click="selectMood(emoji.id)">
                </i>
              </p>
            </div>
          </transition>
        </div>
      </div>
    </div>
    <br>
    <select
      v-model="selectedPeriod"
      class=" fill-height col-auto me-2 my-auto px-3 py-0 subtitle-1 ignore-vuetify-classes"
      @click="loadMoodsByPeriod()">
      <option disabled value="">Please select one</option>
      <option>Lastweek</option>
      <option>Lastmonth</option>
      <option>Lastquarter</option>
      <option>Lastsimester</option>
    </select>
    <span>Selected: {{ selectedPeriod }}</span>
    <br><br>
    <div id="chart">
      <GChart
        v-if="selectedPeriod"
        type="PieChart"
        :options="options"
        :data="chartData" />
    </div>
  </div>
</template>

<script>
import {GChart} from "vue-google-charts";
export default {
  components: {
    GChart
  },
  data: () => ({
    chartData: [
      ["MOOD", "VALUE"],
      ["EXCITED", 0],
      ["SMILE", 0],
      ["SPEECHLESS", 0],
      ["CRYING", 0],
      ["SAD", 0],
    ],
    options: {
      chartArea:{right:90,top:8,width:'100%',height:'100%'},
      backgroundColor: { fill:'transparent' },
      width: 470,
      height: 300,
      slices: {
        0: { color: '#00E676' },
        1: { color: '#8BC34A' },
        2: { color: '#FF0' },
        3: { color: '#FF7043' },
        4: { color: '#FF3D00' },
      }
    },
    emoticons: [
      {
        id: 0,
        name: 'EXCITED',
        iconName: 'mdi mdi-emoticon-excited-outline',
        color: '#00E676',
        isMoodSelected: false,
      },
      {
        id: 1,
        name: 'SMILE',
        iconName: 'mdi mdi-emoticon-happy-outline',
        color: '#8BC34A',
        isMoodSelected: false,
      },
      {
        id: 2,
        name: 'SPEECHLESS',
        iconName: 'far fa-grin-hearts',
        color: '#FF0',
        isMoodSelected: false,
      },
      {
        id: 3,
        name: 'CRYING',
        iconName: 'mdi mdi-emoticon-cry-outline',
        color: '#FF7043',
        isMoodSelected: false,
      },
      {
        id: 4,
        name: 'SAD',
        iconName: 'mdi mdi-emoticon-sad-outline',
        color: '#FF3D00',
        isMoodSelected: false,
      },
    ],
    selectedMoodName: '',
    newId: '',
    selectedPeriod: '',
    displayEmoticons: false,
  }),
  computed: {
    selectedMood() {
      return this.emoticons.find(emoji => emoji.name === this.selectedMoodName);
    },
    moodText() {
      return this.selectedMoodName;
    },
    displayForUser() {
      return eXo.env.portal.profileOwner === eXo.env.portal.userName
    },
  },

  created: function () {
    this.$moodService.getMood(eXo.env.portal.profileOwner).then((mood) => {
      if (mood === null){
        this.displayEmoticons = true;
      }
      this.selectedMoodName = mood.mood;
      this.selectedMood.isMoodSelected = true;
    })
  },

  methods: {
    toggleMoodsForm: function () {
      this.displayEmoticons = !this.displayEmoticons;
    },
    loadMoodsByPeriod: function () {
      this.$moodService.loadMoods(eXo.env.portal.userName, this.selectedPeriod).then((content) => {
        for (let i = 0; i < content.length; i++) {
          // for every period delete old data and add new ones
          this.chartData[i+1].splice(1, 1, content[i].length);
        }
      });
    },
    selectMood: function (id) {

      if (this.selectedMood) {
        this.updateMood(id);
      } else {
        this.saveMood(id);
      }
    },
    updateMood: function (id) {
      this.$moodService.updateMood(this.emoticons[id].name).then((updatedMood) => {
        this.selectedMoodName = updatedMood.mood;
        this.emoticons.forEach(emoticon => emoticon.isMoodSelected = false);
        this.selectedMood.isMoodSelected = true;
        this.toggleMoodsForm();
      });
    },
    saveMood: function (id) {
      this.$moodService.saveMood(this.emoticons[id].name).then((savedMood) => {
        this.selectedMoodName = savedMood.mood;
        this.emoticons.forEach(emoticon => emoticon.isMoodSelected = false);
        this.selectedMood.isMoodSelected = true;
        this.toggleMoodsForm();
      });
    }
  }
}
</script>